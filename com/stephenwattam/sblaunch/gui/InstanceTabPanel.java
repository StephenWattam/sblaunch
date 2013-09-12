

package com.stephenwattam.sblaunch.gui;

import com.stephenwattam.sblaunch.platform.*;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;
import javax.swing.filechooser.*;
import java.awt.Font;

public class InstanceTabPanel extends TabPanel implements ActionListener{

    private static final String KILL_TEXT   = "Abort (kill)";
    private static final String DISOWN_TEXT  = "Disown";
    private static final String CLOSE_TEXT  = "Close Tab";

    private static final String KILL_DIALOG_MESSAGE     = "This will kill the current scuttlebutt instance.  Continue?";
    private static final String KILL_DIALOG_TITLE       = "Kill running instance?";

    private JButton killButton  = new JButton(KILL_TEXT);
    private JButton closeButton = new JButton(CLOSE_TEXT);
    private JButton disownButton = new JButton(DISOWN_TEXT);

    private JLabel statusLabel  = new JLabel("STUFF AND THINGS");

    // Timer config
    private static final int TIMER_DELAY    = 200;
    private Timer timer                     = null;

    // The actual process instance we are reporting.
    private Instance instance;

    public InstanceTabPanel(JTabbedPane tabContainer, String name, Instance instance){
        super(tabContainer, name);

        // Record the instance we be running, and the container for removing
        this.instance = instance;
        
        // Set up JLabel
        /* statusLabel.setLineWrap(true); */
        Font font = new Font("Monospaced", Font.BOLD, 14);
        statusLabel.setFont(font);
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        updateStatus();

        // Configure actions
        killButton.setActionCommand("kill_instance");
        disownButton.setActionCommand("disown_instance");
        closeButton.setActionCommand("close_tab");

        // Make sure the browse buttons go here, 
        // but the run button goes to the main panel
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


		// Kill button 
		gbc.gridwidth = 1;
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.ipadx = 0;
		gbc.ipady = 10;
		gbc.weightx = 0.25;
		gbc.weighty = 0.1;
        /* gbc.insets  = new Insets(10,10,0,10); */
		this.add(disownButton,gbc);

		// Close button 
		gbc.gridwidth = 1;
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.ipadx = 0;
		gbc.ipady = 10;
		gbc.weightx = 0.25;
		gbc.weighty = 0.1;
        /* gbc.insets  = new Insets(10,10,0,10); */
		this.add(killButton,gbc);

		// Kill button 
		gbc.gridwidth = 1;
		gbc.gridx = 0;
		gbc.gridy = 3;
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
        }else if(Ae.getActionCommand().equals("update_status")){
            updateStatus();
        }else{
            System.err.println("Unable to find handler for action command: " + Ae.getActionCommand().toString());
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

        String str = "<html>Script: " + instance.getScript() + "<br>Input: " + instance.getInput() + "<br>Output: " + instance.getOutput();
        switch(instance.exitValue()){
            case 0:
                str = str + "<br>Exited cleanly :-)";
                killButton.setEnabled(false);
                disownButton.setEnabled(false);
                break;
            case -1:
                str = str + "<br>Still running...";
                break;
            default:
                str = str + "<br><br>Exited with code " + instance.exitValue();
                killButton.setEnabled(false);
                disownButton.setEnabled(false);
                break;
        }
        str = str + "</html>";

    
        statusLabel.setText(str);
    }

}
