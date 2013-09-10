
package com.stephenwattam.sblaunch.platform;


import java.util.*;


public class UnixPlatform extends Platform{

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
        command.add("sb");      // TODO: make this global
        
        // Create a process creating creator
        ProcessBuilder builder = new ProcessBuilder("xterm", "-hold", "-e", "sb", script, input, output);

        // Instantiate an instance
        return new Instance(input, script, output, builder.start());
    }

}

/* /home/extremetomato/working/git/scuttlebutt/resources/guardian.sbs */
/* /home/extremetomato/working/git/scuttlebutt/testing/guardian.1.csv */

