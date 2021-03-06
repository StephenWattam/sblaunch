
package com.stephenwattam.sblaunch.platform;
import java.lang.Process;
import java.io.File;

public class Instance{

    // Working directory
    private File wd       = null;

    // Input params
    private String input    = "";
    private String script   = "";
    private String output   = "";

    // Process handle
    private Process process = null;

    public Instance(File wd, String input, String script, String output, Process process){
        this.wd        = wd;

        this.input      = input;
        this.script     = script;
        this.output     = output;

        this.process    = process;
    }


    /** Is the process currently running? */
    public boolean alive(){
        // Try to read exit value. if this throws an exception then it's still alive
        try{
            process.exitValue();
        }catch(IllegalThreadStateException ITSe){
            return true;
        }
        return false;
    }

    /** Returns the return value from the process, or -1 if it's still running. */
    public int exitValue(){
        int retval = 0;

        try{
            retval = process.exitValue();
        }catch(IllegalThreadStateException ITSe){
            return -1;
        }
        return retval;
    }

    public void kill(){
        process.destroy();
    }

    public File getWD(){
        return wd;
    }

    public Process getProcess(){
        return process;
    }

    public String getInput(){
        return input;
    }

    public String getOutput(){
        return output;
    }

    public String getScript(){
        return script;
    }
}

