

package com.stephenwattam.sblaunch.gui;
import com.stephenwattam.sblaunch.platform.*;

import javax.swing.*;

public class SBGUIDialog{

    /** Create a new launcher GUI and display it to the user.
     */
    public SBGUIDialog(String title, String msg){
        JOptionPane.showMessageDialog(null, msg, title, JOptionPane.ERROR_MESSAGE);
    }

}

