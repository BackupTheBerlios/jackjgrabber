package presentation.recordInfo;
/*
 * 
 * 
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc., 675 Mass
 * Ave, Cambridge, MA 02139, USA.
 *  
 */

/**
 * shows all available files in the store directory
 * 
 * @author Reinhard Achleitner
 */
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.text.*;
import java.util.*;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import javax.swing.tree.*;

import presentation.*;
import presentation.GuiTab;

import service.SerHelper;

import com.jgoodies.forms.builder.*;
import com.jgoodies.forms.layout.*;

import control.*;

public class GuiTabAvailableFiles extends GuiTab {

	private ControlRecordInfoTab control;

		private JTree fileTree;

	private DefaultTreeModel treeModel;

	public GuiTabAvailableFiles(ControlRecordInfoTab control) {
		this.setControl(control);
		initialize();
	}

	/**
	 * erzeugt die GUI
	 *  
	 */
	protected void initialize() {
		setLayout(new BorderLayout());

		JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		add(split, BorderLayout.CENTER);

		treeModel = new DefaultTreeModel(new BaseTreeNode(ControlMain.getProperty("label_recordPath")));
		
		fileTree = new JTree(treeModel);
		fileTree.addMouseListener(control);
		fileTree.setCellRenderer(new GuiAvailableFilesTreeRenderer());
		split.setLeftComponent(new JScrollPane(fileTree));
	}

	/**
	 * @return ControlProgramTab
	 */
	public ControlRecordInfoTab getControl() {
		return control;
	}

	/**
	 * Sets the control.
	 * 
	 * @param control
	 *            The control to set
	 */
	public void setControl(ControlRecordInfoTab control) {
		this.control = control;
	}

	public DefaultTreeModel getTreeModel() {
		return treeModel;
	}

	/**
	 * @return
	 */
	public JTree getTree() {
		return fileTree;
	}

}