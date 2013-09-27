

package com.stephenwattam.sblaunch.gui;

import com.stephenwattam.sblaunch.platform.*;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;
import javax.swing.filechooser.*;
import java.awt.Font;
import java.io.File;

public class InstanceTabPanel extends TabPanel implements ActionListener{

    private static final String LOG_TEXT     = "Open log";
    private static final String KILL_TEXT    = "Abort (kill)";
    private static final String DISOWN_TEXT  = "Disown";
    private static final String CLOSE_TEXT   = "Close Tab";

    private static final String KILL_DIALOG_MESSAGE     = "This will kill the current scuttlebutt instance.  Continue?";
    private static final String KILL_DIALOG_TITLE       = "Kill running instance?";

    private JButton logButton       = new JButton(LOG_TEXT);
    private JButton killButton      = new JButton(KILL_TEXT);
    private JButton closeButton     = new JButton(CLOSE_TEXT);
    private JButton disownButton    = new JButton(DISOWN_TEXT);

    private JLabel statusLabel  = new JLabel("STUFF AND THINGS");

    // Timer config
    private static final int TIMER_DELAY    = 200;
    private Timer timer                     = null;

    // The actual process instance we are reporting.
    private Instance instance;
    private Platform platform;

    public InstanceTabPanel(JTabbedPane tabContainer, String name, Instance instance, Platform platform){
        super(tabContainer, name);

        // Record the instance we be running, and the container for removing
        this.instance = instance;
        this.platform = platform; // handle to open a text file
        
        // Set up JLabel
        /* statusLabel.setLineWrap(true); */
        Font font = new Font("Monospaced", 0, 14);
        statusLabel.setFont(font);
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        updateStatus();

        // Configure actions
        logButton.setActionCommand("open_log");
        killButton.setActionCommand("kill_instance");
        disownButton.setActionCommand("disown_instance");
        closeButton.setActionCommand("close_tab");

        // Make sure the browse buttons go here, 
        // but the run button goes to the main panel
        logButton.addActionListener(this);
        killButton.addActionListener(this);
        closeButton.addActionListener(this);
        disownButton.addActionListener(this);


        // Set a timer to update the status
        timer = new Timer(TIMER_DELAY, this);
        timer.setActionCommand("update_status");
        timer.setInitialDelay(200);
        timer.start();
            

        // Configure layout
        GridBagConstraints gbc = new GridBagConstraints();
		setLayout(new GridBagLayout());

		gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets  = new Insets(10,10,10,10);
        gbc.fill   = GridBagConstraints.HORIZONTAL;

		// Status JLabel
		gbc.gridwidth = 1;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.ipadx = 0;
		gbc.ipady = 10;
		gbc.weightx = 0.25;
		gbc.weighty = 0.1;
        /* gbc.insets  = new Insets(10,10,0,10); */
		this.add(statusLabel,gbc);


		// Open button 
		gbc.gridwidth = 1;
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.ipadx = 0;
		gbc.ipady = 10;
		gbc.weightx = 0.25;
		gbc.weighty = 0.1;
        /* gbc.insets  = new Insets(10,10,0,10); */
		this.add(logButton,gbc);

		// Kill button 
		gbc.gridwidth = 1;
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.ipadx = 0;
		gbc.ipady = 10;
		gbc.weightx = 0.25;
		gbc.weighty = 0.1;
        /* gbc.insets  = new Insets(10,10,0,10); */
		this.add(disownButton,gbc);

		// Close button 
		gbc.gridwidth = 1;
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.ipadx = 0;
		gbc.ipady = 10;
		gbc.weightx = 0.25;
		gbc.weighty = 0.1;
        /* gbc.insets  = new Insets(10,10,0,10); */
		this.add(killButton,gbc);

		// Kill button 
		gbc.gridwidth = 1;
		gbc.gridx = 0;
		gbc.gridy = 4;
		gbc.ipadx = 0;
		gbc.ipady = 10;
		gbc.weightx = 0.25;
		gbc.weighty = 0.1;
        /* gbc.insets  = new Insets(10,10,0,10); */
		this.add(closeButton,gbc);




        setVisible(true);
    }


    /**fired when a component performs an action whilst this class is listening to it.
      @param Ae the action even generated
     */
    public void actionPerformed(ActionEvent Ae){
        if(Ae.getActionCommand().equals("kill_instance")){
            killInstance();
        }else if(Ae.getActionCommand().equals("close_tab")){
            closeMe(true);
        }else if(Ae.getActionCommand().equals("disown_instance")){
            closeMe(false);
        }else if(Ae.getActionCommand().equals("open_log")){
            openLog();
        }else if(Ae.getActionCommand().equals("update_status")){
            updateStatus();
        }else{
            System.err.println("Unable to find handler for action command: " + Ae.getActionCommand().toString());
        }
    }

    private void openLog(){

        File debug_path = new File(instance.getWD(), "debug.log");                // TODO: configurability...

        // Open a text file
        if(!platform.openTextFile(debug_path)){
            new SBGUIDialog("Error!", "Failed to open text file automatically, sorry.");
        }

    }


    // Kills the currently-running scuttlebutt instance
    private boolean killInstance(){

        if(!instance.alive())
            return true;


        int reply = JOptionPane.showConfirmDialog(null, KILL_DIALOG_MESSAGE, KILL_DIALOG_TITLE, JOptionPane.YES_NO_OPTION);
        if (reply == JOptionPane.YES_OPTION)
        {
            instance.kill();
            return true;
        }


        return false;
        // TODO: kill the instance if it's running and check with user
    }

    // Close the tab and remove from the parent
    private void closeMe(boolean tryToKill){
        // TODO: kill scuttlebutt if requested
        if(!tryToKill || killInstance()){
            tabContainer.remove(this);
        }
    }

    /* Update the status monitor in the window */
    public void updateStatus(){

        File debug_path = new File(instance.getWD(), "debug.log");  // TODO: configurability...

        String str = "<html><b>Script:</b> " + instance.getScript() + 
                       "<br><b>Input:</b> " + instance.getInput() + 
                       "<br><b>Output:</b> " + instance.getOutput() + 
                       "<br><b>Log:</b>" + debug_path.getPath() + 
                       "<br>";
        switch(instance.exitValue()){
            case 0:
                str = str + "<br><b><font color='green'>Exited cleanly</font></b>";
                killButton.setEnabled(false);
                disownButton.setEnabled(false);
                break;
            case -1:
                str = str + "<br><b>Still running...</b>";
                break;
            default:
                str = str + "<br><br><b><font color='red'>Exited with code " + instance.exitValue() + "</font></b>";
                killButton.setEnabled(false);
                disownButton.setEnabled(false);
                break;
        }
        str = str + "</html>";

    
        statusLabel.setText(str);
    }

}
