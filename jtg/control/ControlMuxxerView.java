package control;
/*
ControlMuxxerView.java by Geist Alexander 

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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JCheckBox;

import model.BOAfterRecordOptions;
import presentation.muxxer.GuiMuxxerView;
import service.SerExternalProcessHandler;
import service.SerProcessStopListener;

public class ControlMuxxerView implements ActionListener, SerProcessStopListener{

	GuiMuxxerView view;
	BOAfterRecordOptions options;
    ArrayList files;

	public ControlMuxxerView(BOAfterRecordOptions options, ArrayList files) {
        this.setOptions(options);
        this.setFiles(files);
		this.setView(new GuiMuxxerView(this));
		this.initialize();
		
		view.setVisible(true);
	}
    
    public ControlMuxxerView(BOAfterRecordOptions options) {
        this.setOptions(options);
        this.setView(new GuiMuxxerView(this));
        this.initialize();
        
        view.setVisible(true);
    }

	private void initialize() {
        switch (this.getOptions().getMplexOption()) {
            case 3: this.getView().getRbMPEG().setSelected(true);
            break;
            case 4: this.getView().getRbSVCD().setSelected(true);
            break;
            case 9: this.getView().getRbDVD().setSelected(true);
        };
        this.getView().getCbStartPX().setSelected(this.getOptions().isUseProjectX());
        this.getView().getCbStartMplex().setSelected(this.getOptions().isUseMplex());
	}
	
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		if (action.equals("ok")) {
		    this.actionOk();
		}
        if (action.equals("Mpeg")) {
            this.getOptions().setMplexOption(3);
        }
        if (action.equals("SVCD")) {
            this.getOptions().setMplexOption(4);
        }
        if (action.equals("DVD")) {
            this.getOptions().setMplexOption(9);
        }
        if (action.equals("cbStartPX")) {
            this.getOptions().setUseProjectX(((JCheckBox)e.getSource()).isSelected());
        }
		if (action.equals("cbStartMplex")) {
            this.getOptions().setUseMplex(((JCheckBox)e.getSource()).isSelected());
		}
	}
    
    private void actionOk() {
        if (this.getFiles()==null || this.getFiles().size()<0) {
            this.getView().dispose();
        } else {
            this.startMuxxing();
        }
        
    }
    
    private void startMuxxing() {
        if(this.getView().getCbStartPX().isSelected()) {
            this.startProjectX(); 
        } else if (this.getView().getCbStartMplex().isSelected()) {
            this.startMplex(); 
        }
    }
    
    private void startMplex() {
        String[] param = new String[7 + files.size()];
        param[0] = ControlMain.getSettingsPath().getMplexPath();
        param[1] = "-v";
        param[2] = "1";
        param[3] = "-f";
        param[4] = Integer.toString(this.getOptions().getMplexOption());
        param[5] = "-o";
        param[6] = this.getOutputFileName((File)files.get(0));
        for (int i = 0; i < files.size(); i++) {
            param[i + 7] = ((File) files.get(i)).getAbsolutePath();
        }
        SerExternalProcessHandler.startProcess(this, "mplex", param, true);
    }
    
    private String getOutputFileName(File file) {
        String name = file.getName();
        File out = new File(name+".mpeg");
        return out.getAbsolutePath();
    }
    
    public void startProjectX() {
        String[] param = new String[3 + files.size()];
        String separator = System.getProperty("file.separator");

        param[0] = System.getProperty("java.home") + separator + "bin" + separator + "java";
        param[1] = "-jar";
        param[2] = ControlMain.getSettingsPath().getProjectXPath();

        for (int i = 0; i < files.size(); i++) {
            param[i + 3] = ((File) files.get(i)).getAbsolutePath();
        }
        SerExternalProcessHandler.startProcess(this, "ProjectX", param, true);
    }
    
    public void processStopped(int exitCode, String processName) {
        if (processName.equals("ProjectX") && this.getView().getCbStartMplex().isSelected()) {
          //mplex starten mit demuxten Files  
        }
        if (processName.equals("mplex")) {
            this.getView().dispose();
        }
        
    }

	/**
	 * @return Returns the options.
	 */
	public BOAfterRecordOptions getOptions() {
		return options;
	}
	/**
	 * @param options The options to set.
	 */
	public void setOptions(BOAfterRecordOptions options) {
		this.options = options;
	}
    /**
     * @return Returns the files.
     */
    public ArrayList getFiles() {
        return files;
    }
    /**
     * @param files The files to set.
     */
    public void setFiles(ArrayList files) {
        this.files = files;
    }
    /**
     * @return Returns the view.
     */
    public GuiMuxxerView getView() {
        return view;
    }
    /**
     * @param view The view to set.
     */
    public void setView(GuiMuxxerView view) {
        this.view = view;
    }
}