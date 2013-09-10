
package com.stephenwattam.sblaunch.platform;


import java.util.Vector;


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
        System.out.println("STUB: getInstance in UnixPlatform");
        return null;
    }

}


