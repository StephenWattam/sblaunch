
package com.stephenwattam.sblaunch.platform;
import java.lang.Process;

public class Instance{

    // Input params
    private String input    = "";
    private String script   = "";
    private String output   = "";

    // Process handle
    private Process process = null;

    public Instance(String input, String script, String output, Process process){
        this.input      = input;
        this.script     = script;
        this.output     = output;

        this.process    = process;
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

