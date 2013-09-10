
package com.stephenwattam.sblaunch.gui;
import javax.swing.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.awt.image.BufferedImage;
import javax.swing.event.*;
import java.awt.geom.*;
import javax.imageio.*;
import javax.swing.filechooser.*;
import java.security.Permission;

public class TabFrame extends JFrame implements WindowListener{

    private TabPanel panel;
    private JButton reattachButton = new JButton("X"); //new ImageIcon(MazeUISettingsManager.attachIcon));

	public TabFrame(TabPanel panel){
		super(panel.getName());
        this.panel = panel;

		//super.setIconImage(new ImageIcon(ICON_PATH).getImage());
		setPreferredSize(new Dimension(400, 300));
		setMinimumSize(new Dimension(400, 300));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(true);
        /* this.setIconImage(MazeUISettingsManager.icon); */

		setContentPane(panel);
		setVisible(true);

        this.addWindowListener(this);
	}

    public void dispose(){
        this.setContentPane(new JPanel());
        super.dispose();
    }

    // WindowListener
    public void windowClosing(WindowEvent e) { /* displayMessage("Window closing", e); */ 
        panel.attach();
    }
    public void windowClosed(WindowEvent e) {/* displayMessage("Window closed", e); */}
    public void windowOpened(WindowEvent e) { /* displayMessage("Window opened", e); */ }
    public void windowIconified(WindowEvent e) { /* displayMessage("Window iconified", e); */ }
    public void windowDeiconified(WindowEvent e) { /* displayMessage("Window deiconified", e); */ }
    public void windowActivated(WindowEvent e) { /* displayMessage("Window activated", e); */ }
    public void windowDeactivated(WindowEvent e) {/* displayMessage("Window deactivated", e); */ }



}






