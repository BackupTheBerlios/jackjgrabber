package control;
/*

 This program is free software; you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation; either version 2, or (at your option)
 any later version.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with this program; if not, write to the Free Software
 Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.  

 */

/**
 * Kontrollklasse für die Aufnahme Infos
 * @author Reinhard Achleitner
 */
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.text.*;
import java.util.*;

import javax.swing.*;
import javax.swing.tree.*;

import model.*;

import org.apache.log4j.*;

import presentation.*;
import presentation.recordInfo.*;
import service.*;

/**
 * Controlklasse des Programmtabs.
 */
public class ControlRecordInfoTab extends ControlTab implements ActionListener, MouseListener {

	GuiMainView mainView;

	private GuiTabRecordInfo guiTabRecordInfo;
	private File directory;
	private javax.swing.Timer fileInfoTimer;
	private javax.swing.Timer availableFileTimer;

	private Date currentStartForBitrate; // Starttime when the first file has been written (for bitrate calculation)
	private Date currentStartBegin;

	private double maxBitRateValue;
	private double minBitRateValue = Double.MAX_VALUE;

	private static final int REFRESH_TIME = 1000; // Refresh time of fileinfos
	private static final int REFRESH_TIME_READALLFILES = 30000; // Refresh time of fileinfos
	private static final int BITRATECALCSTART = 5; // seconds after record start the birate will be calculated

	private static final String LOGFILENAME = "log.txt";

	public ControlRecordInfoTab(GuiMainView view) {
		this.setMainView(view);

		if (availableFileTimer == null) {
			availableFileTimer = new javax.swing.Timer(REFRESH_TIME_READALLFILES, new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					if (guiTabRecordInfo.isShowing()) {
						reloadAvailableFiles();
					}
				}
			});
		}
		availableFileTimer.start();

	}

	/**
	 * loads all available files in the store directory and shows them in a table
	 *  
	 */
	protected void reloadAvailableFiles() {

		DefaultTreeModel model = guiTabRecordInfo.getTreeModel();
		DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getRoot();

		JTree tree = guiTabRecordInfo.getTree();
		Enumeration enNodes = tree.getExpandedDescendants(new TreePath(root));
		TreePath[] selNodes = tree.getSelectionPaths();
		root.removeAllChildren();

		File f = new File(ControlMain.getSettings().getSavePath());

		createStructure(root, f);

		if (guiTabRecordInfo != null) {
			model.reload();
		}

		if (enNodes != null) {
			while (enNodes.hasMoreElements()) {
				TreePath element = (TreePath) enNodes.nextElement();
				Object[] path = element.getPath();
				/*
				 * for (int i = 1; i < path.length; i++) { DefaultMutableTreeNode node = (DefaultMutableTreeNode) path[path.l]; if (node !=
				 * null) { expandNode(tree, node.getUserObject().toString(), root); } }
				 */
				BaseTreeNode node = (BaseTreeNode) path[path.length - 1];
				expandNode(tree, node, root);
			}
		}

		if (selNodes != null) {
			for (int i = 0; i < selNodes.length; i++) {
				BaseTreeNode node = (BaseTreeNode) selNodes[i].getLastPathComponent();
				selectNode(tree, node, root);
			}
		}
	}

	/**
	 * @param string
	 * @param root
	 */
	private void expandNode(JTree tree, BaseTreeNode nodeToExpand, DefaultMutableTreeNode root) {
		int iCount = root.getChildCount();
		for (int i = 0; i < iCount; i++) {
			BaseTreeNode node = (BaseTreeNode) root.getChildAt(i);
			if (node.getIdent().equals(nodeToExpand.getIdent())) {
				tree.expandPath(new TreePath(node.getPath()));

			} else if (node.getChildCount() > 0) {
				expandNode(tree, nodeToExpand, node);
			}
		}
	}

	/**
	 * @param string
	 * @param root
	 */
	private void selectNode(JTree tree, BaseTreeNode nodeToExpand, DefaultMutableTreeNode root) {
		int iCount = root.getChildCount();
		for (int i = 0; i < iCount; i++) {
			BaseTreeNode node = (BaseTreeNode) root.getChildAt(i);
			if (node.getIdent().equals(nodeToExpand.getIdent())) {
				tree.addSelectionPath(new TreePath(node.getPath()));

			} else if (node.getChildCount() > 0) {
				selectNode(tree, nodeToExpand, node);
			}
		}
	}

	/**
	 * @param root
	 * @param f
	 */
	private void createStructure(DefaultMutableTreeNode parent, File f) {
		File[] files = f.listFiles();
		DateFormat format = SimpleDateFormat.getDateTimeInstance();

		FileTypeTreeNode videoNode = new FileTypeTreeNode(ControlMain.getProperty("label_recordVideo"), FileTypeTreeNode.VIDEO);
		FileTypeTreeNode audioNode = new FileTypeTreeNode(ControlMain.getProperty("label_recordAudio"), FileTypeTreeNode.AUDIO);
		FileTypeTreeNode otherNode = new FileTypeTreeNode(ControlMain.getProperty("label_recordOther"), FileTypeTreeNode.OTHER);
		videoNode.setIdent("Video:" + parent.getUserObject().toString());
		audioNode.setIdent("Audio:" + parent.getUserObject().toString());
		otherNode.setIdent("Other:" + parent.getUserObject().toString());
		parent.add(videoNode);
		parent.add(audioNode);
		parent.add(otherNode);

		for (int i = 0; i < files.length; i++) {

			if (!files[i].getName().startsWith(".")) {

				if (files[i].isDirectory()) {
					BaseTreeNode node = new BaseTreeNode(new BOFileWrapper(files[i]));
					node.setIdent("Directory:" + files[i].getName());
					parent.add(node);
					createStructure(node, files[i]);
				} else {
					if (SerHelper.isVideo(files[i].getName())) {
						BaseTreeNode node = new BaseTreeNode(new BOFileWrapper(files[i]));
						videoNode.add(node);
					} else if (SerHelper.isAudio(files[i].getName())) {
						BaseTreeNode node = new BaseTreeNode(new BOFileWrapper(files[i]));
						audioNode.add(node);
					} else {
						BaseTreeNode node = new BaseTreeNode(new BOFileWrapper(files[i]));
						otherNode.add(node);
					}
				}
			}
		}

		if (videoNode.getChildCount() == 0) {
			parent.remove(videoNode);
		}
		if (audioNode.getChildCount() == 0) {
			parent.remove(audioNode);
		}
		if (otherNode.getChildCount() == 0) {
			parent.remove(otherNode);
		}

	} /*
	   * (non-Javadoc)
	   * 
	   * @see control.ControlTab#getMainView()
	   */
	public GuiMainView getMainView() {
		return mainView;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see control.ControlTab#setMainView(presentation.GuiMainView)
	 */
	public void setMainView(GuiMainView view) {
		mainView = view;

	}

	public void setRecordView(GuiTabRecordInfo tabRecordInfo) {
		guiTabRecordInfo = tabRecordInfo;
		reloadAvailableFiles();

	}

	/**
	 * Setzt die Aufnahmeinfos
	 * 
	 * @param title
	 * @param engine
	 * @param directory
	 * @param timer
	 */
	public void startRecord(String title, String engine, File directory, boolean timer) {

		this.directory = directory;
		SerLogAppender.getTextAreas().add(guiTabRecordInfo.getLogArea());

		if (timer) {
			title = ControlMain.getProperty("label_recordTimer") + ": " + title;
		} else {
			title = ControlMain.getProperty("label_recordDirect") + ": " + title;
		}
		guiTabRecordInfo.startRecord(title, engine, directory, timer);

		//		 Erzeuge Timer der periodisch die Dateiinfos aktualisiert
		if (fileInfoTimer == null) {
			fileInfoTimer = new javax.swing.Timer(REFRESH_TIME, new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					reloadFileInfos();
				}

			});
		}
		fileInfoTimer.start();
		currentStartBegin = new Date();
	}

	/**
	 * stoppt die Erstellung der Aufnahmeinfos
	 * 
	 *  
	 */
	public void stopRecord() {

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		if (currentStartBegin == null) {
			return;
		}

		if (fileInfoTimer != null && fileInfoTimer.isRunning()) {
			fileInfoTimer.stop();
		}

		SerLogAppender.getTextAreas().remove(guiTabRecordInfo.getLogArea());

		Date now = new Date();
		long minutes = (now.getTime() - currentStartBegin.getTime()) / 1000 / 60;

		String text = ControlMain.getProperty("label_recordStopped") + " (" + SimpleDateFormat.getTimeInstance().format(currentStartBegin);
		text += " - " + SimpleDateFormat.getTimeInstance().format(new Date()) + " Dauer: " + minutes + " min)";

		currentStartForBitrate = null;
		currentStartBegin = null;

		guiTabRecordInfo.stopRecord(text);

		if (ControlMain.getSettings().isStoreLogAfterRecord()) {
			saveLog();
		}
	}

	/**
	 * speichert das Log der Aufnahme
	 *  
	 */
	private void saveLog() {
		PrintStream print = null;
		try {

			String file = directory.getAbsolutePath() + File.separatorChar + LOGFILENAME;

			File f = new File(file);

			print = new PrintStream(new FileOutputStream(file));

			StringBuffer log = new StringBuffer();
			log.append(guiTabRecordInfo.getTitle() + "\n");
			log.append(guiTabRecordInfo.getVideo() + "\n");
			log.append(guiTabRecordInfo.getAudio() + "\n");
			log.append(guiTabRecordInfo.getLog());

			StringTokenizer tok = new StringTokenizer(log.toString(), "\n");
			while (tok.hasMoreTokens()) {
				print.println(tok.nextToken());
			}

		} catch (Exception e) {
			e.printStackTrace();
			Logger.getLogger("RecordControl").error(e);
		} finally {
			if (print != null) {
				print.close();
			}
		}
	}

	/**
	 * aktualisiert die Dateiinformationen (wird vom Timer aufgerufen)
	 *  
	 */
	protected void reloadFileInfos() {

		// Lade alle Files
		File[] aFiles = directory.listFiles();

		StringBuffer video = new StringBuffer();
		StringBuffer audio = new StringBuffer();
		StringBuffer other = new StringBuffer();
		int videoCount = 0;
		int audioCount = 0;
		int otherCount = 0;

		double videoSize = 0;

		for (int i = 0; i < aFiles.length; i++) {
			String size = SerHelper.calcSize(aFiles[i].length(), "MB");

			String end = getEnd(aFiles[i]);
			if (SerHelper.isVideo(aFiles[i].getName())) {
				if (currentStartForBitrate == null) {
					currentStartForBitrate = new Date();
				}
				videoCount++;
				video.append("Video " + end + " (" + videoCount + ")  : " + size + "\n");
				videoSize += aFiles[i].length();

			} else if (SerHelper.isAudio(aFiles[i].getName())) {
				audioCount++;
				audio.append("Audio " + " " + end + " (" + audioCount + ")  : " + size + "\n");
			} else {
				size = SerHelper.calcSize(aFiles[i].length(), "KB");
				otherCount++;
				other.append(aFiles[i].getName() + ":      " + size + "\n");
			}
		}

		guiTabRecordInfo.setVideo(video.toString());
		guiTabRecordInfo.setAudio(audio.toString());
		guiTabRecordInfo.setOther(other.toString());

		refreshBitrate(videoSize);
	}

	/**
	 * @param videoSize
	 */
	private void refreshBitrate(double videoSize) {
		// Calc average videorate
		if (currentStartForBitrate != null) {
			Date now = new Date();
			long seconds = (now.getTime() - currentStartForBitrate.getTime()) / 1000;
			if (seconds > BITRATECALCSTART) {
				double videoSizePerSecond = videoSize / seconds;
				if (minBitRateValue > videoSizePerSecond && videoSizePerSecond > 0) {
					minBitRateValue = videoSizePerSecond;
				}
				if (maxBitRateValue < videoSizePerSecond) {
					maxBitRateValue = videoSizePerSecond;
				}

				guiTabRecordInfo.setBitrate(SerHelper.calcSize(videoSizePerSecond, "MBit", 1000), SerHelper.calcSize(minBitRateValue,
						"MBit", 1000), SerHelper.calcSize(maxBitRateValue, "MBit", 1000));
			}

		}
	}

	/**
	 * @param file
	 * @return
	 */
	private String getEnd(File file) {

		String fileName = file.getAbsolutePath();
		int end = fileName.lastIndexOf(".");
		if (end > -1) {
			return fileName.substring(end + 1);
		}
		return "";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	public void run() {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
	 */
	public void mouseClicked(MouseEvent e) {

		Object s = e.getSource();
		if (s instanceof JTree) {
			if (e.getClickCount() == 2 && SwingUtilities.isLeftMouseButton(e)) {

				TreePath sel = ((JTree) s).getSelectionPath();
				if (sel != null && sel.getLastPathComponent() != null) {
					BOFileWrapper file = (BOFileWrapper) ((DefaultMutableTreeNode) sel.getLastPathComponent()).getUserObject();
					openFile(file);

				}
			}

		}
	}

	/**
	 * @param file
	 */
	private void openFile(BOFileWrapper file) {
		if (SerHelper.isVideo(file.getName()) || SerHelper.isAudio(file.getName())) {
			ArrayList player = ControlMain.getSettings().getPlaybackOptions();

			if (player.size() > 0) {
				BOPlaybackOption play = null;
				if (player.size() > 1) {
					// choosing player
					JList list = new JList();
					DefaultListModel m = new DefaultListModel();
					list.setModel(m);
					BOPlaybackOption def = null;
					for (int i = 0; i < player.size(); i++) {
						BOPlaybackOption opt = (BOPlaybackOption) player.get(i);
						m.addElement(opt);
						if (opt.isStandard().booleanValue()) {
							def = opt;
						}
					}

					if (def != null) {
						list.setSelectedValue(def, true);
					} else {
						list.setSelectedIndex(0);
					}

					String message = ControlMain.getProperty("msg_choosePlayer");

					int res = JOptionPane.showOptionDialog(guiTabRecordInfo, new Object[]{message, new JScrollPane(list)}, "Player", 0,
							JOptionPane.QUESTION_MESSAGE, null, new String[]{ControlMain.getProperty("button_playback"),
									ControlMain.getProperty("button_cancel")}, "Play");
					if (res == 0) {
						play = (BOPlaybackOption) list.getSelectedValue();
					}

				} else {
					play = (BOPlaybackOption) player.get(0);
				}

				if (play != null) {
					String exec = play.getExecString();
					exec += " " + file.getAbsolutePath();

					try {
						Logger.getLogger("ControlProgramTab").info(exec);
						Process run = Runtime.getRuntime().exec(exec);
					} catch (IOException e) {

						Logger.getLogger("ControlProgramTab").error(e.getMessage());
					}
				}
			}

		} else {
			try {
				//read the file
				FileInputStream in = new FileInputStream(file.getAbsoluteFile());
				BufferedInputStream inS = new BufferedInputStream(in);
				byte[] bytes = new byte[inS.available()];
				inS.read(bytes);
				String text = new String(bytes);
				JTextArea textArea = new JTextArea();
				textArea.setWrapStyleWord(true);
				textArea.setLineWrap(true);
				textArea.setEditable(false);
				textArea.setText(text);
				JScrollPane scroll = new JScrollPane(textArea);
				scroll.setPreferredSize(new Dimension(400, 400));
				JOptionPane.showMessageDialog(guiTabRecordInfo, scroll, "File", JOptionPane.INFORMATION_MESSAGE);

			} catch (Exception e) {
				Logger.getLogger("ControlProgramTab").error(e.getMessage());
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
	 */
	public void mousePressed(MouseEvent e) {
		TreePath p = guiTabRecordInfo.getTree().getClosestPathForLocation(e.getX(), e.getY());
		if (p != null) {
			if (e.isControlDown() || e.isShiftDown()) {
				guiTabRecordInfo.getTree().addSelectionPath(p);
			} else {
				guiTabRecordInfo.getTree().setSelectionPath(p);
			}
		}
		if (SwingUtilities.isRightMouseButton(e)) {
			showPopup((JTree) e.getSource(), e);
		}

	}

	/**
	 * @param tree
	 */
	private void showPopup(JTree tree, MouseEvent e) {

		JPopupMenu m = new JPopupMenu();
		int count = tree.getSelectionCount();
		if (count > 0) {
			Action deleteAction = new AbstractAction(ControlMain.getProperty("button_delete")) {
				public void actionPerformed(ActionEvent e) {
					deleteSelected();
				}
			};

			m.add(new JMenuItem(deleteAction));
		}
		if (count == 1) {
			final Object obj = ((BaseTreeNode) tree.getSelectionPath().getLastPathComponent()).getUserObject();
			
			if (obj instanceof BOFileWrapper) {
				final BOFileWrapper file = (BOFileWrapper) obj;
				
				m.add(new JMenuItem(new AbstractAction(ControlMain.getProperty("button_rename")) {
					public void actionPerformed(ActionEvent e) {
						renameSelected();
					}
				}));

				if (SerHelper.isVideo(((BOFileWrapper) obj).getName())) {
					/*
					 * m.add(new JMenuItem(new AbstractAction("Muxxi") { public void actionPerformed(ActionEvent e) {
					 * startMuxxi(((BOFileWrapper)obj).getAbsoluteFile()); } }));
					 */

				}
				
				String name = file.getName();
				if (SerHelper.isVideo(name) || SerHelper.isAudio(name))
				{
					m.add(new JMenuItem(new AbstractAction(ControlMain.getProperty("button_playback")) {
						public void actionPerformed(ActionEvent e) {
							openFile(file);
						}
					}));
				}
				else
				{
					if (file != null && !file.isDirectory())
					{
						m.add(new JMenuItem(new AbstractAction(ControlMain.getProperty("open")) {
							public void actionPerformed(ActionEvent e) {
								openFile(file);
							}
						}));
					}
				}
				

			}
		}
		m.show(tree, e.getX(), e.getY());
	}

	/**
	 * @param absoluteFile
	 */
	private void startMuxxi(File file) {

		String execMuxxi = "C:\\Programme\\D-Box\\DVDAuthorMuxxi\\Muxxi.exe -i " + file.getAbsolutePath() + " -out DVD";
		Process run;
		try {
			run = Runtime.getRuntime().exec(execMuxxi);
			Logger.getLogger("ControlProgramTab").info(execMuxxi);
			new SerInputStreamReadThread(true, run.getInputStream()).start();
			new SerErrorStreamReadThread(true, run.getErrorStream()).start();

		} catch (IOException e) {
			Logger.getLogger("ControlProgramTab").error(e.getMessage());
			e.printStackTrace();
		}

	}

	/**
	 *  
	 */
	protected void renameSelected() {
		JTree tree = guiTabRecordInfo.getTree();
		TreePath sel = tree.getSelectionPath();
		if (sel != null) {
			BOFileWrapper obj = (BOFileWrapper) ((BaseTreeNode) tree.getSelectionPath().getLastPathComponent()).getUserObject();
			if (obj != null) {
				File f = obj.getAbsoluteFile();
				String newName = JOptionPane.showInputDialog(guiTabRecordInfo, ControlMain.getProperty("msg_rename"), f.getName());
				if (newName != null && newName.length() > 0) {
					File newFile = new File(f.getParent(), newName);
					obj.getAbsoluteFile().renameTo(newFile);
				}
			}
		}
		reloadAvailableFiles();
	}

	/**
	 *  
	 */
	protected void deleteSelected() {
		HashSet filesToDelete = new HashSet();

		// Delete all selected files
		JTree tree = guiTabRecordInfo.getTree();
		TreePath[] sel = tree.getSelectionPaths();
		for (int i = 0; i < sel.length; i++) {
			BaseTreeNode nodeToDelete = (BaseTreeNode) sel[i].getLastPathComponent();
			deleteChildNodes(nodeToDelete, filesToDelete);
		}

		// Directory has to be deleted last
		Iterator it = filesToDelete.iterator();
		Vector toDelete = new Vector();
		while (it.hasNext()) {
			File element = (File) it.next();
			if (element.isDirectory()) {
				toDelete.add(element);
			} else {
				toDelete.insertElementAt(element, 0);
			}
		}

		Iterator iter = toDelete.iterator();

		while (iter.hasNext()) {
			File element = (File) iter.next();
			if (!element.delete()) {
				JOptionPane.showMessageDialog(guiTabRecordInfo, element.getAbsolutePath() + " konnte nicht gelöscht werden!");
			}
		}
		reloadAvailableFiles();

	}

	/**
	 * @param nodeToDelete
	 */
	private void deleteChildNodes(BaseTreeNode nodeToDelete, HashSet filesToDelete) {

		if (nodeToDelete.getChildCount() > 0) {
			Enumeration childs = nodeToDelete.children();
			while (childs.hasMoreElements()) {
				BaseTreeNode element = (BaseTreeNode) childs.nextElement();
				deleteChildNodes(element, filesToDelete);
			}

		}

		// delete file
		Object obj = nodeToDelete.getUserObject();
		if (obj instanceof BOFileWrapper) {
			File f = ((BOFileWrapper) obj).getAbsoluteFile();
			filesToDelete.add(f);

		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
	 */
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
	 */
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
	 */
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}