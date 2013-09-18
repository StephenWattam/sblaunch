
package com.stephenwattam.sblaunch.gui;
import com.stephenwattam.sblaunch.platform.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.filechooser.*;


public class RunTabPanel extends TabPanel implements ActionListener{

    private static final String INPUT_LABEL_TEXT    = "<html><b>Input CSV</b></html>";
    private static final String OUTPUT_LABEL_TEXT   = "<html><b>Output CSV</b></html>";
    private static final String SCRIPT_LABEL_TEXT   = "<html><b>Script</b></html>";
    private static final String BROWSE_TEXT         = "Browse...";
    private static final String RUN_TEXT            = "<html><b>Make stuff go!</b></html>";

    // Ruby version string
    private static final String VERSION_TEXT   = "<html><b>Versions</b></html>";
    private static final String RUBY_VERSION_TEXT   = "<html><font color='green'>%s</font></html>";
    private static final String RUBY_MISSING_TEXT   = "<html><font color='red'>Ruby not installed!</font></html>";
    private static final String SB_VERSION_TEXT     = "<html><font color='green'>%s</font></html>";
    private static final String SB_MISSING_TEXT     = "<html><font color='red'>Scuttlebutt not installed!</font></html>";

    // Version labels
    private JLabel vLabel           = new JLabel(VERSION_TEXT);
    private JLabel rubyVLabel       = new JLabel(RUBY_VERSION_TEXT);
    private JLabel sbVLabel         = new JLabel(SB_VERSION_TEXT);

    // Three JLabels, and three input fields
    private JLabel inputLabel       = new JLabel(INPUT_LABEL_TEXT);
    private JLabel outputLabel      = new JLabel(OUTPUT_LABEL_TEXT);
    private JLabel scriptLabel      = new JLabel(SCRIPT_LABEL_TEXT);

    private JTextField inputField   = new JTextField();
    private JTextField outputField  = new JTextField();
    private JTextField scriptField  = new JTextField();

    private JButton inputButton     = new JButton(BROWSE_TEXT);
    private JButton outputButton    = new JButton(BROWSE_TEXT);
    private JButton scriptButton    = new JButton(BROWSE_TEXT);
    
    private JButton runButton       = new JButton(RUN_TEXT);

    public RunTabPanel(JTabbedPane tabContainer, String name, ActionListener parent, Platform platform){
        super(tabContainer, name);


        // Configure actions
        inputButton.setActionCommand("input_browse");
        outputButton.setActionCommand("output_browse");
        scriptButton.setActionCommand("script_browse");
        runButton.setActionCommand("run_scuttlebutt");

        // Make sure the browse buttons go here, 
        // but the run button goes to the main panel
        inputButton.addActionListener(this);
        outputButton.addActionListener(this);
        scriptButton.addActionListener(this);
        runButton.addActionListener(parent);


        // Update version labels
        updateVersionLabels(platform);

        // Configure layout
        GridBagConstraints gbc = new GridBagConstraints();
		setLayout(new GridBagLayout());


		gbc.anchor  = GridBagConstraints.CENTER;
        gbc.fill    = GridBagConstraints.HORIZONTAL;
        gbc.insets  = new Insets(2,2,2,2);
		// Input label and field
        
		gbc.gridwidth = 3;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.ipadx = 0;
		gbc.ipady = 10;
		gbc.weightx = 0.75;
        /* gbc.insets  = new Insets(10,10,0,10); */
		this.add(vLabel,gbc);
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.ipadx = 0;
		gbc.ipady = 10;
		gbc.weightx = 0.75;
        gbc.insets  = new Insets(10,10,0,10);
		this.add(rubyVLabel,gbc);
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.ipadx = 0;
		gbc.ipady = 10;
		gbc.weightx = 0.75;
        /* gbc.insets  = new Insets(10,10,0,10); */
		this.add(sbVLabel,gbc);

        /* Space */
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.ipadx = 0;
		gbc.ipady = 10;
		gbc.weightx = 0.75;
        gbc.insets  = new Insets(2,2,2,2);
		this.add(new JSeparator(JSeparator.HORIZONTAL),gbc);
        
        
		gbc.gridwidth = 1;
		gbc.gridx = 1;
		gbc.gridy = 4;
		gbc.ipadx = 0;
		gbc.ipady = 10;
		gbc.weightx = 0.75;
        /* gbc.insets  = new Insets(10,10,0,10); */
		this.add(inputLabel,gbc);
		gbc.gridx = 1;
		gbc.gridy = 5;
		gbc.ipadx = 0;
		gbc.ipady = 10;
        /* gbc.insets  = new Insets(0,10,10,10); */
		this.add(inputField,gbc);
		gbc.gridx = 2;
		gbc.gridy = 5;
		gbc.ipadx = 0;
		gbc.ipady = 10;
		gbc.weightx = 0.25;
        /* gbc.insets  = new Insets(0,10,10,10); */
		this.add(inputButton,gbc);

		// Script label and field
		/* gbc.gridwidth = 1; */
		gbc.gridx = 1;
		gbc.gridy = 6;
		gbc.ipadx = 0;
		gbc.ipady = 10;
		gbc.weightx = 0.75;
        /* gbc.insets  = new Insets(10,10,0,10); */
		this.add(scriptLabel,gbc);
		gbc.gridx = 1;
		gbc.gridy = 7;
		gbc.ipadx = 0;
		gbc.ipady = 10;
        /* gbc.insets  = new Insets(0,10,10,10); */
		this.add(scriptField,gbc);
		gbc.gridx = 2;
		gbc.gridy = 7;
		gbc.ipadx = 0;
		gbc.ipady = 10;
		gbc.weightx = 0.25;
        /* gbc.insets  = new Insets(0,10,10,10); */
		this.add(scriptButton,gbc);

		// Output label and field
		/* gbc.gridwidth = 1; */
		gbc.gridx = 1;
		gbc.gridy = 8;
		gbc.ipadx = 0;
		gbc.ipady = 10;
		gbc.weightx = 0.75;
        /* gbc.insets  = new Insets(10,10,0,10); */
		this.add(outputLabel,gbc);
		gbc.gridx = 1;
		gbc.gridy = 9;
		gbc.ipadx = 0;
		gbc.ipady = 10;
        /* gbc.insets  = new Insets(0,10,10,10); */
		this.add(outputField,gbc);
		gbc.gridx = 2;
		gbc.gridy = 9;
		gbc.ipadx = 0;
		gbc.ipady = 10;
		gbc.weightx = 0.25;
        /* gbc.insets  = new Insets(0,10,10,10); */
		this.add(outputButton,gbc);


        /* Space */
		gbc.gridwidth = 3;
		gbc.gridx = 0;
		gbc.gridy = 10;
		gbc.ipadx = 0;
		gbc.ipady = 10;
		gbc.weightx = 1;
        /* gbc.insets  = new Insets(10,10,0,10); */
		this.add(new JSeparator(JSeparator.HORIZONTAL),gbc);


        // Run button
		gbc.gridx = 1;
		gbc.gridy = 11;
		gbc.ipadx = 0;
		gbc.ipady = 10;
		gbc.weightx = 0.25;
        gbc.insets  = new Insets(10,10,10,10);
		this.add(runButton,gbc);


        setVisible(true);
    }


    /**fired when a component performs an action whilst this class is listening to it.
      @param Ae the action even generated
     */
    public void actionPerformed(ActionEvent Ae){
        if(Ae.getActionCommand().equals("input_browse")){
            inputField.setText( browseFile(new FileNameExtensionFilter("CSV Files", "csv")) );
        }else if(Ae.getActionCommand().equals("output_browse")){
            outputField.setText( browseFile(new FileNameExtensionFilter("CSV Files", "csv")) );
        }else if(Ae.getActionCommand().equals("script_browse")){
            scriptField.setText( browseFile(new FileNameExtensionFilter("Scuttlebutt Scripts", "sbs")) );
        }else{
            System.err.println("Unable to find handler for action command: " + Ae.getActionCommand().toString());
        }
    }


    // Browse for a file
    private String browseFile(FileFilter ff){
        final JFileChooser fileChooser = new JFileChooser();
        fileChooser.setMultiSelectionEnabled(false);
            
        // TODO: filters
        fileChooser.setFileFilter(ff);

        if(fileChooser.showOpenDialog(this) == 0){
            try{
                return fileChooser.getSelectedFile().getCanonicalPath();
            }catch(java.io.IOException IOe){}
        }

        return "";
    }

    // Update version lavels
    private void updateVersionLabels(Platform platform){

        // Read out ruby version
        String rubyVersion = RUBY_MISSING_TEXT;
        if (platform.isRubyInstalled()){
            rubyVersion = String.format(RUBY_VERSION_TEXT, platform.getRubyVersion());
        }
        rubyVLabel.setText(rubyVersion);

        // Read out scuttlebutt version
        String sbVersion = SB_MISSING_TEXT;
        if (platform.isScuttlebuttInstalled()){
            sbVersion = String.format(RUBY_VERSION_TEXT, platform.getScuttlebuttVersion());
        }
        sbVLabel.setText(sbVersion);


    }

    
    // Update version lavels}

    // Accessors for in, out, script paths
    public String getInputPath(){
        return inputField.getText();
    }

    public String getOutputPath(){
        return outputField.getText();
    }

    public String getScriptPath(){
        return scriptField.getText();
    }

}
