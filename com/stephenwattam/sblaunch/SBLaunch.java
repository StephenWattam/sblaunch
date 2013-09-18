
package com.stephenwattam.sblaunch;

import com.stephenwattam.sblaunch.gui.*;
import com.stephenwattam.sblaunch.platform.*;

class SBLaunch{

    public static final String VERSION = "0.0.1a (18-09-13)";

    public static void main(String[] args){
        System.out.println("Scuttlebutt cross-platform launch UI.");
        System.out.println("By Stephen Wattam <stephenwattam.com>");
        System.out.println("");
        System.out.println("You know this app runs as a GUI, right?");


        // Detect platform and load the appropriate platform manager
        OSValidator osv = new OSValidator();
        Platform    pf  = null;

        if(osv.isWindows()){
            System.out.println("Aren't you tired of Windows yet?");
            pf = new WindowsPlatform();
        }else if(osv.isMac()){
            System.out.println("You have a very expensive fruit-related machine.");
            new SBGUIDialog("Warning!", "Mac support needs xterm right now (I'm working on a fix)..."); // FIXME
            pf = new MacPlatform();
        }else if(osv.isUnix() || osv.isSolaris()){
            System.out.println("It seems you're running a UNICS derivative.  Good choice.");
            pf = new UnixPlatform();
        }else{
            // TODO: FIXME: make this a GUI message
            new SBGUIDialog("Error!", "Your operating system is not supported. Sorry.");
            System.exit(1);
        }

        SBLaunchGUI gui = new SBLaunchGUI(VERSION, pf);
    }



    /* With thanks to http://www.mkyong.com/java/how-to-detect-os-in-java-systemgetpropertyosname/ */

    static class OSValidator {

        private String OS = System.getProperty("os.name").toLowerCase();

        public boolean isWindows() {
            return (OS.indexOf("win") >= 0);
        }

        public boolean isMac() {
            return (OS.indexOf("mac") >= 0);
        }

        public boolean isUnix() {
            return (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0 );
        }

        public boolean isSolaris() {
            return (OS.indexOf("sunos") >= 0);
        }

    }



}
