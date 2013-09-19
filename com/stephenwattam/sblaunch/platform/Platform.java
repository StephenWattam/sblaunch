
package com.stephenwattam.sblaunch.platform;

import java.lang.Process;

import java.util.*;
import java.io.*;

// Represents an operating system to the system
//
// No instantiatey
public abstract class Platform{

    // Working directory in which to run sb instance
    static final File WORKING_DIRECTORY = new File(System.getProperty("java.io.tmpdir"));

    // Check if ruby is installed and in the path
    public boolean isRubyInstalled(){
        String[] command = {"ruby", "--version"};
        CommandResult result = quickCommand(command);

        return (result.getReturnValue() != -1);
    }

    // Get the ruby version string
    public String getRubyVersion(){
        String[] command = {"ruby", "--version"};
        CommandResult result = quickCommand(command);

        /* if result.getReturnValue() == -1 */
        /* throw new IOException("Ruby isn't installed."); */

        return result.getStdout();
    }

    // Check if the sb binary is installed and in the path
    public boolean isScuttlebuttInstalled(){
        String[] command = {"sb", "--version"};
        CommandResult result = quickCommand(command);

        return (result.getReturnValue() != -1);
    }

    // Get the version of the sb binary installed
    public String getScuttlebuttVersion(){
        String[] command = {"sb", "--version"};
        CommandResult result = quickCommand(command);

        /* if result.getReturnValue() == -1 */
        /* throw new IOException("Ruby isn't installed."); */

        return result.getStdout();
    }


    // Return an instance object to execute SB, and record in a list
    public Instance getInstance(String input, String script, String output) throws java.io.IOException{
        System.out.println("STUB: getInstance in Platform");
        return null;
    }


    // Run a command quickly
    protected CommandResult quickCommand(String[] command){

        // Build the factory
        ProcessBuilder builder = new ProcessBuilder(command);
        builder.directory(WORKING_DIRECTORY);

        // Run the thing
        int exitValue = -1;
        StringBuilder sb        = new StringBuilder();
    	System.out.printf("---\nRunning %s:\n", Arrays.toString(command));
        try{
            Process proc = builder.start();

            //Read out dir output
            InputStreamReader isr   = new InputStreamReader(proc.getInputStream());
            BufferedReader br       = new BufferedReader(isr);

            String line;
            System.out.printf("Output of running %s is:\n", Arrays.toString(command));
            while ((line = br.readLine()) != null) {
                System.out.println(line);
                sb.append(line);
                sb.append("\n");
            }

            //Wait to get exit value
            try {
                exitValue = proc.waitFor();
		System.out.println("Result: " + exitValue);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } catch(IOException IOe){
	    System.out.println("IO Exception: " + IOe.getMessage());
            return new CommandResult(-1, "", IOe.getMessage());
        }
        return new CommandResult(exitValue, sb.toString(), "");
    }


    // Private class for the result of a command
    class CommandResult{

        private int retval      = 0;
        private String stdout   = null;
        private String stderr   = null;

        public CommandResult(int retval, String stdout, String stderr){
            this.retval = retval;
            this.stdout = stdout;
            this.stderr = stderr;
        }

        public int getReturnValue(){
            return retval;
        }

        public String getStdout(){
            return this.stdout;
        }

        public String getStderr(){
            return this.stderr;
        }
    }


}
