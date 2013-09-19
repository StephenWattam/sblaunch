
package com.stephenwattam.sblaunch.platform;



import java.util.*;
import java.io.File;

public class WindowsPlatform extends Platform{


    // Check if ruby is installed and in the path
    public boolean isRubyInstalled(){
        String[] command = {"cmd", "/c", "ruby", "--version"};
        CommandResult result = quickCommand(command);

        return (result.getReturnValue() != -1);
    }

    // Get the ruby version string
    public String getRubyVersion(){
        String[] command = {"cmd", "/c", "ruby", "--version"};
        CommandResult result = quickCommand(command);

        /* if result.getReturnValue() == -1 */
        /* throw new IOException("Ruby isn't installed."); */

        return result.getStdout();
    }


    // Check if the sb binary is installed and in the path
    public boolean isScuttlebuttInstalled(){
        String[] command = {"cmd", "/c", "sb", "--version"};
        CommandResult result = quickCommand(command);

        return (result.getReturnValue() != -1);
    }

    // Get the version of the sb binary installed
    public String getScuttlebuttVersion(){
        String[] command = {"cmd", "/c", "sb", "--version"};
        CommandResult result = quickCommand(command);

        /* if result.getReturnValue() == -1 */
        /* throw new IOException("Ruby isn't installed."); */

        return result.getStdout();
    }



    // Return an instance object to execute SB, and record in a list
    public Instance getInstance(String input, String script, String output) throws java.io.IOException{

	/* This is an EPIC hack, and does some crazy shit that I don't think should even
	   work in any shell system.

	   It uses cmd's builtin 'start' to launch a new window that runs scuttlebutt,
	   and then (because start deparents) it waits for it to finish then reads
	   the pseudo-envar %errorlevel% to mine the return code from the child of
	   'start'.  Since start deparents, %errorlevel% should always be 0, but 
	   Windows does something weird and passes it back up. */

        // Construct the command
        List<String> command = new ArrayList<String>();

	// Shell 1
        command.add("cmd");
        command.add("/c");
        command.add("start");
        command.add("/wait");

	// Shell 2
        command.add("cmd");
        command.add("/U");
        command.add("/T:17");
        command.add("/c");

	// Scuttlebutt
        command.add("sb");
        command.add(script);
        command.add(input);
        command.add(output);

	// Shell 1 again
        command.add("&");
        command.add("exit %errorlevel%");

        
        // Create a process creating creator
        ProcessBuilder builder = new ProcessBuilder(command);
        builder.directory(WORKING_DIRECTORY);

        // Instantiate an instance
        return new Instance(WORKING_DIRECTORY, input, script, output, builder.start());
    }
}

