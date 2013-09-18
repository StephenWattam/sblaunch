

package com.stephenwattam.sblaunch.gui;
import com.stephenwattam.sblaunch.platform.*;

import javax.swing.*;
import java.awt.Dimension;
import java.awt.event.*;
import java.util.*;

public class SBLaunchGUI extends JFrame implements ActionListener{

    // Store the platform we're on
    private Platform pf = null;

    /**The menu bar*/
    private JMenuBar            menuBar;
    /**The tab handler which holds all of the panels*/
    private JTabbedPane         tabs            = new DnDTabbedPane();


    // Keep track of panels
    private Vector<TabPanel>        panels          = new Vector<TabPanel>();
    private RunTabPanel              runTab          = null;

    private static final int WIDTH = 400;
    private static final int HEIGHT = 500;


    private String version = null;

    /** Create a new launcher GUI and display it to the user.
     */
    public SBLaunchGUI(String version, Platform pf){
        super("Scuttlebutt Launcher v" + version);
        this.version = version;

        // Store platform we're on
        this.pf = pf;

        // Make the GUI easier on the eye
        setLookAndFeel();



        // Configure size
        setSize(new Dimension(WIDTH, HEIGHT));
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        setResizable(true);


        // Set up the menu
        menuBar = buildMenu();
        setJMenuBar(menuBar);


        // Set up tabs (they add themselves)
        runTab         = new RunTabPanel(tabs, "Run SB", this, pf);
        panels.add(runTab); 
        
        // Select the first tab
        tabs.setSelectedIndex(0);


        // Display ourselves
        setIconImage(new ImageIcon(getClass().getResource("/resources/images/icon.png")).getImage());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(tabs);
        pack();
        setVisible(true);

    }

    // Builds a single menu item
    private JMenuItem buildMenuItem(String label, String actionCommand){
        JMenuItem menuItem = new JMenuItem(label);
        menuItem.setActionCommand(actionCommand);
        menuItem.addActionListener(this);
        return menuItem;
    }

    // Builds the whole drop-down menu
    private JMenuBar buildMenu(){
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        /*fileMenu.add(buildMenuItem("Analyse","analyse"));*/
        fileMenu.add(buildMenuItem("Exit","exit_all"));
        menuBar.add(fileMenu);


        JMenu tabMenu = new JMenu("Panels");
        tabMenu.add(buildMenuItem("Attach All","attach_all"));
        tabMenu.add(buildMenuItem("Detach All","detach_all"));
        menuBar.add(tabMenu);

        // RHS
        menuBar.add(Box.createHorizontalGlue());

        JMenu helpMenu = new JMenu("Help");
        helpMenu.add(buildMenuItem("About...","about"));
        menuBar.add(helpMenu);

        return menuBar;
    }


    /** Sets the look and feel of the UI components to something that is hopefully native. */
    private void setLookAndFeel(){
		// For windows, try to acquire native L+F
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); 
		}catch(Exception e) {}

		// For linux, attempt to access GTK.  Falls through if on windows,
		try{
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
		}catch(Exception e){}
    }


    /**fired when a component performs an action whilst this class is listening to it.
      @param Ae the action even generated
     */
    public void actionPerformed(ActionEvent Ae){
        if(Ae.getActionCommand().equals("exit_all")){
            quit();
        }else if(Ae.getActionCommand().equals("about")){
            helpAbout();
        }else if(Ae.getActionCommand().equals("attach_all")){
            attachTabs();
        }else if(Ae.getActionCommand().equals("detach_all")){
            detachTabs();
        }else if(Ae.getActionCommand().equals("run_scuttlebutt")){
            newInstance();
        }else{
            System.err.println("Unable to find handler for action command: " + Ae.getActionCommand().toString());
        }

        /* updatePanes(); */
    }


    private void attachTabs(){
        for(TabPanel tp : panels){
            if(!tp.isAttached())
                tp.attach();
        }
    }

    private void detachTabs(){
        for(TabPanel tp : panels){
            if(tp.isAttached())
                tp.detach();
        }
    }


    // What it's all about.  Launch scuttlebutt.
    private void newInstance(){
        // TODO: use Platform object to launch the exe.

        String input  = runTab.getInputPath();
        String script = runTab.getScriptPath();
        String output = runTab.getOutputPath();


        // Create a new instance to handle the execution
        try{
            Instance instance        = pf.getInstance(input, script, output);

            // Create a new panel and give it an execution instance
            TabPanel instancePanel   = new InstanceTabPanel(tabs, "Scuttlebutt", instance); // TODO: nicer name
        }catch(java.io.IOException IOe){
            new SBGUIDialog("Error!", "Failed to start scuttlebutt: " + IOe.getMessage());
        }
    }

    // Display simple about dialog
    private void helpAbout(){
        JOptionPane.showMessageDialog(this, 
                "<html>Scuttlebutt launcher <b>v " + this.version + "</b><br>by Stephen Wattam, <br>stephenwattam.com</html>", 
                "About", 
                JOptionPane.INFORMATION_MESSAGE);
    }


    private void quit(){
        // TODO: ensure nothing is still running in the platform
        System.exit(0); // TODO: send window close event instead?
    }

}



/*  */
/*  */
/* package maize.ui; */
/* import javax.swing.*; */
/* import javax.swing.*; */
/* import java.awt.*; */
/* import java.awt.event.*; */
/* import java.io.*; */
/* import java.awt.image.BufferedImage; */
/* import javax.swing.event.*; */
/* import java.awt.geom.*; */
/* import javax.imageio.*; */
/* import javax.swing.filechooser.*; */
/* import java.security.Permission; */
/* import java.util.*; */
/* import java.lang.reflect.ReflectPermission; */
/*  */
/* import maize.*; */
/* public class MazeUI extends JFrame implements ActionListener, WindowListener{ */
/*  */
/*     // state */
/*     private MazeTest            mazeTest        = null; */
/*     /**The menu bar* / */
/*     private JMenuBar            menuBar; */
/*     /**The tab handler which holds all of the panels* / */
/*     private JTabbedPane         tabs            = new DnDTabbedPane(); */
/*  */
/*  */
/*     // Panels, and a list of them */
/*     private Vector<TabPanel>    panels          = new Vector<TabPanel>(); */
/*     private MazeTabPanel        mazeTab; */
/*     private BotTabPanel         botTab; */
/*     private MultiTestTabPanel   multiTestTab; */
/*     private LogTabPanel         logTab; */
/*  */
/*  */
/*     public MazeUI(MazeTest mazeTest){ */
/*         super("Maize UI"); */
/*         Log.log("Starting Maize UI..."); */
/*  */
/*         // Initialize our security manager nice and early */
/* 	    System.setSecurityManager( new maize.ui.BotSecurityManager() ); */
/*  */
/*         /* setSize(MazeUISettingsManager.uiWidth, MazeUISettingsManager.uiHeight); * / */
/*         setPreferredSize(new Dimension(MazeUISettingsManager.uiWidth, MazeUISettingsManager.uiHeight)); */
/*         setMinimumSize(new Dimension(MazeUISettingsManager.uiMinWidth, MazeUISettingsManager.uiMinHeight)); */
/*         setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); */
/*         setResizable(true); */
/*         this.setIconImage(MazeUISettingsManager.icon); */
/*         this.addWindowListener(this); */
/*  */
/*  */
/*         this.mazeTest = mazeTest; */
/*         BotCompilerHelper.compileAndLoadBots(this.mazeTest, MazeUISettingsManager.botPackageName, MazeUISettingsManager.botDirectory); */
/*         constructDefaultMazes(); */
/*  */
/*  */
/*         //menu */
/*         menuBar = buildMenu(); */
/*         this.setJMenuBar(menuBar); */
/*  */
/*  */
/*  */
/*         //tabs */
/*         mazeTab         = new MazeTabPanel      (mazeTest, tabs, "Manage Mazes"); */
/*         botTab          = new BotTabPanel       (mazeTest, tabs, "Manage Bots"); */
/*         multiTestTab    = new MultiTestTabPanel (mazeTest, tabs, "Run Tests"); */
/*         logTab          = new LogTabPanel       (mazeTest, tabs, "Log"); */
/*  */
/*  */
/*  */
/*         // List all panels for easy use later */
/*         panels.add(mazeTab); */
/*         panels.add(botTab); */
/*         panels.add(multiTestTab); */
/*         panels.add(logTab); */
/*  */
/*         // Select the first tab */
/*         tabs.setSelectedIndex(0); */
/*  */
/*         // Attach the log */
/*         Log.addLogListener(logTab); */
/*  */
/*         updatePanes(); */
/*  */
/*         // Set part of the window and make us visible */
/*         setContentPane(tabs); */
/*         this.pack(); */
/*         setVisible(true); */
/*  */
/*         Log.log("Maize UI running."); */
/*     } */
/*  */
/*     // Create a set of default mazes, one for each factory */
/*     private void constructDefaultMazes(){ */
/*         Log.log("Constructing default mazes"); */
/*         for(MazeFactory mf : this.mazeTest.factories){ */
/*             Maze m = mf.getMaze(MazeUISettingsManager.defaultMazeWidth, MazeUISettingsManager.defaultMazeHeight); */
/*             m.setName("Default " + mf.getClass().getName()); */
/*             mazeTest.mazes.add( m ); */
/*         } */
/*     } */
/*  */
/*     // Builds a single menu item */
/*     private JMenuItem buildMenuItem(String label, String actionCommand){ */
/*         JMenuItem menuItem = new JMenuItem(label); */
/*         menuItem.setActionCommand(actionCommand); */
/*         menuItem.addActionListener(this); */
/*         return menuItem; */
/*     } */
/*  */
/*     // Builds the whole drop-down menu */
/*     private JMenuBar buildMenu(){ */
/*         JMenuBar menuBar = new JMenuBar(); */
/*  */
/*         JMenu fileMenu = new JMenu("File"); */
/*         /*fileMenu.add(buildMenuItem("Analyse","analyse"));* / */
/*         fileMenu.add(buildMenuItem("Exit","exit_all")); */
/*         menuBar.add(fileMenu); */
/*  */
/*  */
/*         JMenu botMenu = new JMenu("Bots"); */
/*         botMenu.add(buildMenuItem("Reload all bots (" + MazeUISettingsManager.botDirectory + ")","reload_bots")); */
/*         botMenu.add(buildMenuItem("Compile (" + MazeUISettingsManager.botDirectory + ")","compile_bot")); */
/*         botMenu.add(buildMenuItem("Instantiate...","inst_bot_choose")); */
/*         botMenu.add(buildMenuItem("Instantiate (advanced)...","inst_bot")); */
/*         botMenu.add(buildMenuItem("Load...","load_bot")); */
/*         menuBar.add(botMenu); */
/*  */
/*         JMenu mazeMenu = new JMenu("Mazes"); */
/*         mazeMenu.add(buildMenuItem("New...","new_maze")); */
/*         mazeMenu.add(buildMenuItem("Load...","load_maze")); */
/*         menuBar.add(mazeMenu); */
/*          */
/*         JMenu tabMenu = new JMenu("Panels"); */
/*         tabMenu.add(buildMenuItem("Attach All","attach_all")); */
/*         tabMenu.add(buildMenuItem("Detach All","detach_all")); */
/*         menuBar.add(tabMenu); */
/*  */
/*         // RHS */
/*         menuBar.add(Box.createHorizontalGlue()); */
/*  */
/*         JMenu helpMenu = new JMenu("Help"); */
/*         helpMenu.add(buildMenuItem("About...","about")); */
/*         menuBar.add(helpMenu); */
/*  */
/*         return menuBar; */
/*     } */
/*  */
/*  */
/*     /**fired when a component performs an action whilst this class is listening to it. */
/*       @param Ae the action even generated */
/*      * / */
/*     public void actionPerformed(ActionEvent Ae){ */
/*         if(Ae.getActionCommand().equals("exit_all")){ */
/*             quit(); */
/*         }else if(Ae.getActionCommand().equals("new_maze")){ */
/*             new NewMazeDialog(mazeTest, this); */
/*         }else if(Ae.getActionCommand().equals("load_maze")){ */
/*             loadMaze(); */
/*         }else if(Ae.getActionCommand().equals("load_bot")){ */
/*             loadBot(); */
/*         }else if(Ae.getActionCommand().equals("inst_bot")){ */
/*             new NewBotDialog(mazeTest, this, MazeUISettingsManager.botPackageName); */
/*         }else if(Ae.getActionCommand().equals("inst_bot_choose")){ */
/*             compileBot(); */
/*         }else if(Ae.getActionCommand().equals("compile_bot")){ */
/*             BotCompilerHelper.compileAllBots(MazeUISettingsManager.botDirectory);  */
/*         }else if(Ae.getActionCommand().equals("reload_bots")){ */
/*             this.mazeTest.bots.clear(); */
/*             BotCompilerHelper.compileAndLoadBots(this.mazeTest, MazeUISettingsManager.botPackageName, MazeUISettingsManager.botDirectory); */
/*         }else if(Ae.getActionCommand().equals("about")){ */
/*             helpAbout(); */
/*         }else if(Ae.getActionCommand().equals("attach_all")){ */
/*             attachTabs(); */
/*         }else if(Ae.getActionCommand().equals("detach_all")){ */
/*             detachTabs(); */
/*         }else{ */
/*             Log.log("Unable to find handler for action command: " + Ae.getActionCommand().toString()); */
/*         } */
/*  */
/*         updatePanes(); */
/*     } */
/*  */
/*     private void helpAbout(){ */
/*         new AboutDialog(mazeTest, this); */
/*     } */
/*  */
/*     // Load a maze from a serialised file */
/*     private void loadMaze(){ */
/*         final JFileChooser fileChooser = new JFileChooser(); */
/*         fileChooser.setMultiSelectionEnabled(false); */
/*         //fileChooser.setFileFilter(new ImageFileFilter()); */
/*         if(fileChooser.showOpenDialog(this) == 0){ */
/*             try{ */
/*                 Log.log("Loading maze from " + fileChooser.getSelectedFile()); */
/*                 Maze m = (Maze)ClassSerializer.load(fileChooser.getSelectedFile()); */
/*                 mazeTest.mazes.add(m); */
/*                 updatePanes(); */
/*             }catch(Exception e){ */
/*                 JOptionPane.showMessageDialog(this, "Error loading maze."); */
/*                 Log.log("Error loading maze."); */
/*                 Log.logException(e); */
/*             } */
/*         } */
/*     } */
/*  */
/*     // Load a bot from a serialised file */
/*     private void loadBot(){ */
/*         final JFileChooser fileChooser = new JFileChooser(); */
/*         fileChooser.setMultiSelectionEnabled(false); */
/*         //fileChooser.setFileFilter(new ImageFileFilter()); */
/*         if(fileChooser.showOpenDialog(this) == 0){ */
/*             try{ */
/*                 Log.log("Loading bot from " + fileChooser.getSelectedFile()); */
/*                 Bot b = (Bot)ClassSerializer.load(fileChooser.getSelectedFile()); */
/*                 mazeTest.bots.add(b); */
/*                 updatePanes(); */
/*             }catch(Exception e){ */
/*                 JOptionPane.showMessageDialog(this, "Error loading bot."); */
/*                 Log.log("Error loading bot."); */
/*                 Log.logException(e); */
/*             } */
/*         } */
/*     } */
/*  */
/*  */
/*     private void compileBot(){ */
/*         final JFileChooser fileChooser = new JFileChooser(MazeUISettingsManager.botDirectory); */
/*         fileChooser.setMultiSelectionEnabled(false); */
/*  */
/*         // Filter all .java files from the filename */
/*         javax.swing.filechooser.FileFilter filter = new javax.swing.filechooser.FileFilter(){ */
/*             public boolean accept(File file){ */
/*                 String name = file.getName(); */
/*                 return file.canRead() && name.endsWith(".java") && !name.startsWith("."); */
/*             } */
/*  */
/*             public String getDescription(){ */
/*                 return "Java source files only"; */
/*             } */
/*         }; */
/*  */
/*         fileChooser.setFileFilter(filter); */
/*         if(fileChooser.showOpenDialog(this) == 0){ */
/*             try{ */
/*                 String filename = fileChooser.getSelectedFile().getName(); */
/*                 //System.out.println("DEBUG: " + filename); */
/*                 Log.log("Compiling bot from file: " + filename); */
/*                 if(BotCompilerHelper.compile(MazeUISettingsManager.botDirectory + java.io.File.separator + filename)){ */
/*                     mazeTest.bots.add(BotCompilerHelper.loadBotClass(  */
/*                                 MazeUISettingsManager.botPackageName + "." +  */
/*                                 BotCompilerHelper.classNameFromBaseName(filename))); */
/*                     updatePanes(); */
/*                 } */
/*             }catch(Exception e){ */
/*                 JOptionPane.showMessageDialog(this, "Error loading bot."); */
/*                 Log.log("Error loading bot."); */
/*                 Log.logException(e); */
/*             } */
/*         } */
/*  */
/*     } */
/*  */
/*  */
/*  */
/*     // Quit */
/*     private void quit(){ */
/*         Log.removeLogListener(logTab); */
/*  */
/*         // Kill all the panels */
/*         for(TabPanel tp : panels){ */
/*             tp.dispose(); */
/*         } */
/*  */
/*  */
/*         dispose(); */
/*         Log.log("Goodbye."); */
/*         System.exit(0); */
/*     } */
/*  */
/*     // Update all of the panes */
/*     public void updatePanes(){ */
/*         this.mazeTab.update(); */
/*         this.botTab.update(); */
/*         this.multiTestTab.update(); */
/*     } */
/*  */
/*     private void attachTabs(){ */
/*         for(TabPanel tp : panels){ */
/*             if(!tp.isAttached()) */
/*                 tp.attach(); */
/*         } */
/*     } */
/*  */
/*     private void detachTabs(){ */
/*         for(TabPanel tp : panels){ */
/*             if(tp.isAttached()) */
/*                 tp.detach(); */
/*         } */
/*     } */
/*  */
/*     // WindowListener */
/*     public void windowClosing(WindowEvent e) { /* displayMessage("Window closing", e); * /  */
/*         quit(); */
/*     } */
/*     public void windowClosed(WindowEvent e) {/* displayMessage("Window closed", e); * /} */
/*     public void windowOpened(WindowEvent e) { /* displayMessage("Window opened", e); * / } */
/*     public void windowIconified(WindowEvent e) { /* displayMessage("Window iconified", e); * / } */
/*     public void windowDeiconified(WindowEvent e) { /* displayMessage("Window deiconified", e); * / } */
/*     public void windowActivated(WindowEvent e) { /* displayMessage("Window activated", e); * / } */
/*     public void windowDeactivated(WindowEvent e) {/* displayMessage("Window deactivated", e); * / } */
/*  */
/*  */
/*  */
/*  */
/* } */
/*  */
/*  */
/*  */
/*  */




