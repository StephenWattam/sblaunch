
package com.stephenwattam.sblaunch.platform;


import java.util.*;
import java.io.File;

public class UnixPlatform extends Platform{

    // Working directory in which to run sb instance
    private static final File WORKING_DIRECTORY = new File(System.getProperty("java.io.tmpdir"));

    public Vector<Instance> instances = new Vector<Instance>();

    public void checkRubyVersion(){
        System.out.println("STUB: checkRubyVersion in UnixPlatform");
    }

    public void checkGemVersion(){
        System.out.println("STUB: checkGemVersion in UnixPlatform");
    }

    public int countInstances(){
        System.out.println("STUB: countInstances in UnixPlatform");
        return 0;
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

