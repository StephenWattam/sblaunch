
package com.stephenwattam.sblaunch.platform;


import java.util.*;
import java.io.File;

public class UnixPlatform extends Platform{


    

    // Open a text file
    public boolean openTextFile(File filename){
        String[] command = {"xdg-open", filename.getPath()};
        CommandResult result = quickCommand(command);

        return (result.getReturnValue() == 0);
    }

    // Update the scuttlebutt gem
    public void updateScuttlebutt(){

        String[] command = {"xterm", "-hold", "-e", "gem", "install", "--pre", "scuttlebutt"};
        
        quickCommand(command);
    }


    // Return an instance object to execute SB, and record in a list
    public Instance getInstance(String input, String script, String output) throws java.io.IOException{

        // Construct the command
        List<String> command = new ArrayList<String>();
        command.add("xterm");
        command.add("-hold");      // TODO: make this global
        command.add("-e");      
        command.add("sb");      
        command.add(script);
        command.add(input);
        command.add(output);
        
        // Create a process creating creator
        ProcessBuilder builder = new ProcessBuilder(command);
        builder.directory(WORKING_DIRECTORY);

        // Instantiate an instance
        return new Instance(WORKING_DIRECTORY, input, script, output, builder.start());
    }

}

/* /home/extremetomato/working/git/scuttlebutt/resources/guardian.sbs */
/* /home/extremetomato/working/git/scuttlebutt/testing/guardian.1.csv */

