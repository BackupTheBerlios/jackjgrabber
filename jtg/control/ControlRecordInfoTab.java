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
 * Kontrollklasse f�r die Aufnahme Infos
 * @author Reinhard Achleitner
 */
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.text.*;
import java.util.*;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.tree.*;

import model.*;

import org.apache.log4j.*;

import presentation.*;
import presentation.recordInfo.*;
import service.*;
import service.recordinfo.*;

/**
 * Controlklasse des Programmtabs.
 */
public class ControlRecordInfoTab extends ControlTab implements ActionListener, MouseListener, ListSelectionListener {

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

	private AbstractAction refreshAction;

	public ControlRecordInfoTab(GuiMainView view) {
		this.setMainView(view);

		if (availableFileTimer == null) {
			refreshAction = new AbstractAction() {
				public void actionPerformed(ActionEvent e) {
					if (guiTabRecordInfo.isShowing()) {
						reloadAvailableFiles();
					}
				}
			};
			availableFileTimer = new javax.swing.Timer(REFRESH_TIME_READALLFILES, refreshAction);

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

		String savePath = ControlMain.getSettingsRecord().getSavePath();
		if (savePath != null && savePath.length() > 0) {
			File f = new File(savePath);

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
		} else {
			root.setUserObject(ControlMain.getProperty("err_noStorePath"));
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

		for (int i = 0; i < files.length; i++) {

			if (!files[i].getName().startsWith(".")) {

				if (files[i].isDirectory()) {
					BaseTreeNode node = new BaseTreeNode(new BOFileWrapper(files[i]));
					node.setIdent("Directory:" + files[i].getName());
					parent.add(node);
					createStructure(node, files[i]);
				}
			}
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
		Thread t = new Thread(new Runnable() {
			public void run() {
				reloadAvailableFiles();
			}
		});
		t.start();

		//		register refresh key
		KeyStroke stroke = (KeyStroke) KeyStroke.getAWTKeyStroke(KeyEvent.VK_F5, 0);

		guiTabRecordInfo.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(stroke, "F5");
		guiTabRecordInfo.getActionMap().put("F5", refreshAction);

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

		if (ControlMain.getSettingsRecord().isStoreLogAfterRecord()) {
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

		ArrayList video = new ArrayList();
		ArrayList audio = new ArrayList();
		ArrayList other = new ArrayList();
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
				video.add(new BOFileWrapper(aFiles[i], "Video " + end + " (" + videoCount + ")  : " + size + "\n"));
				videoSize += aFiles[i].length();

			} else if (SerHelper.isAudio(aFiles[i].getName())) {
				audioCount++;
				audio.add(new BOFileWrapper(aFiles[i], "Audio " + " " + end + " (" + audioCount + ")  : " + size + "\n"));
			} else {
				size = SerHelper.calcSize(aFiles[i].length(), "KB");
				otherCount++;
				other.add(new BOFileWrapper(aFiles[i], aFiles[i].getName() + ":      " + size + "\n"));
			}
		}

		guiTabRecordInfo.setVideo(video);
		guiTabRecordInfo.setAudio(audio);
		guiTabRecordInfo.setOther(other);

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
		if (e.getSource() instanceof JTable) {
			if (e.getClickCount() == 2) {
				File file = (File) ((JTable) e.getSource()).getModel().getValueAt(((JTable) e.getSource()).getSelectedRow(), 2);
				openFile(file);
			}
		} else if (e.getSource() instanceof JList) {
			if (e.getClickCount() == 2) {
				Object sel = ((JList) e.getSource()).getSelectedValue();
				if (sel instanceof BOFileWrapper) {
					openFile(((BOFileWrapper) sel).getAbsoluteFile());

				}
			}
		}
	}

	/**
	 * @param file
	 */
	private void openFile(File file) {
		if (SerHelper.isVideo(file.getName()) || SerHelper.isAudio(file.getName())) {
			ArrayList player = ControlMain.getSettingsPlayback().getPlaybackOptions();

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

					if (ControlMain.getSettingsPlayback().isAlwaysUseStandardPlayback() && def != null) {
						play = def;
					} else {
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

				if (file.length() > 50000) {
					JOptionPane.showMessageDialog(guiTabRecordInfo, ControlMain.getProperty("err_fileTolarge"));
					return;
				}

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
		if (e.getSource() instanceof JTable) {
			if (SwingUtilities.isRightMouseButton(e)) {
				if (e.getSource() instanceof JTable) {
					showPopup((JTable) e.getSource(), e);
				}
			}
		} else {
			TreePath p = guiTabRecordInfo.getTree().getClosestPathForLocation(e.getX(), e.getY());
			if (p != null) {
				if (e.isControlDown() || e.isShiftDown()) {
					guiTabRecordInfo.getTree().addSelectionPath(p);
				} else {
					guiTabRecordInfo.getTree().setSelectionPath(p);
				}
				if (SwingUtilities.isRightMouseButton(e)) {
					if (e.getSource() instanceof JTree) {
						//showTreePopup((JTree) e.getSource(), e);		not finished
					}
				}
			}
		}

	}

	/**
	 * @param tree
	 */
	private void showPopup(JTable table, MouseEvent e) {

		JPopupMenu m = new JPopupMenu();
		int count = table.getSelectedRowCount();
		if (count == 1) {
			final File file = (File) guiTabRecordInfo.getTableModel().getValueAt(table.getSelectedRow(), 2);

			m.add(new JMenuItem(new AbstractAction(ControlMain.getProperty("button_rename")) {
				public void actionPerformed(ActionEvent e) {
					renameSelected(file);
				}
			}));

			if (SerHelper.isVideo(file.getName())) {

				// deactivated currently only for personal use of crazyreini
				/*m.add(new JMenuItem(new AbstractAction("Muxxi") {
					public void actionPerformed(ActionEvent e) {
						startMuxxi(file);
					}
				}));
				*/

			}

			String name = file.getName();
			if (SerHelper.isVideo(name) || SerHelper.isAudio(name)) {
				m.add(new JMenuItem(new AbstractAction(ControlMain.getProperty("button_playback")) {
					public void actionPerformed(ActionEvent e) {
						openFile(file);
					}
				}));
			} else {
				if (file != null && !file.isDirectory()) {
					m.add(new JMenuItem(new AbstractAction(ControlMain.getProperty("open")) {
						public void actionPerformed(ActionEvent e) {
							openFile(file);
						}
					}));
				}
			}
		}
		if (m.getComponentCount() > 0) {
			m.show(table, e.getX(), e.getY());
		}
	}
	
	
	
	/** not activated yet
	 * @param tree
	 */
	private void showTreePopup(JTree table, MouseEvent e) {

		JPopupMenu m = new JPopupMenu();
		int count = table.getSelectionCount();
		if (count == 1) {
		
			m.add(new JMenuItem(new AbstractAction(ControlMain.getProperty("button_delete")) {
				public void actionPerformed(ActionEvent e) {
					deleteSelected();
				}
			}));

			
		}
		if (m.getComponentCount() > 0) {
			m.show(table, e.getX(), e.getY());
		}
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
	protected void renameSelected(File f) {
		String newName = JOptionPane.showInputDialog(guiTabRecordInfo, ControlMain.getProperty("msg_rename"), f.getName());
		if (newName != null && newName.length() > 0) {
			File newFile = new File(f.getParent(), newName);
			f.renameTo(newFile);
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
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
	 */
	public void mouseEntered(MouseEvent e) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
	 */
	public void mouseExited(MouseEvent e) {
	}

	public void valueChanged(ListSelectionEvent e) {
		if (!e.getValueIsAdjusting()) {
			if (e.getSource() instanceof JList) {

			} else {
				int row = guiTabRecordInfo.getFileTable().getSelectedRow();
				if (row > -1) {
					File info = (File) guiTabRecordInfo.getFileTable().getModel().getValueAt(row, 2);
					
					String values = SerHelper.createFileInfo(info);
					

					guiTabRecordInfo.setFileInfo(values);
				} else {
					guiTabRecordInfo.setFileInfo("");
				}
			}
		}
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
			nodeToDelete.getUserObject();
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
				JOptionPane.showMessageDialog(guiTabRecordInfo, element.getAbsolutePath() + " konnte nicht gel�scht werden!");
			}
		}
		reloadAvailableFiles();
	
	}

}