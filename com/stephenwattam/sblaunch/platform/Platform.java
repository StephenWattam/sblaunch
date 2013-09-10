
package com.stephenwattam.sblaunch.platform;

import java.lang.Process;

// Represents an operating system to the system
//
// No instantiatey
public abstract class Platform{


    public void checkRubyVersion(){
        System.out.println("STUB: checkRubyVersion in Platform");
    }

    public void checkGemVersion(){
        System.out.println("STUB: checkGemVersion in Platform");
    }

    public int countInstances(){
        System.out.println("STUB: countInstances in Platform");
        return 0;
    }

    // Return an instance object to execute SB, and record in a list
    public Instance getInstance(String input, String script, String output) throws java.io.IOException{
        System.out.println("STUB: getInstance in Platform");
        return null;
    }
}
