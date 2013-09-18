
package com.stephenwattam.sblaunch.platform;



import java.util.*;
import java.io.File;

public class WindowsPlatform extends Platform{

    // Return an instance object to execute SB, and record in a list
    public Instance getInstance(String input, String script, String output) throws java.io.IOException{

        // Construct the command
        List<String> command = new ArrayList<String>();
        command.add("cmd");
        command.add("/C");
        command.add("start");
        command.add("cmd");      
        command.add("/U");      
        command.add("/T:17");      
        command.add("/K");      
        /* command.add("date");       */
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

