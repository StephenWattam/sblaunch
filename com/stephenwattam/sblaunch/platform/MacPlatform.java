
package com.stephenwattam.sblaunch.platform;

public class MacPlatform extends Platform{

    public void checkRubyVersion(){
        System.out.println("STUB: checkRubyVersion in MacPlatform");
    }

    public void checkGemVersion(){
        System.out.println("STUB: checkGemVersion in MacPlatform");
    }

    public int countInstances(){
        System.out.println("STUB: countInstances in MacPlatform");
        return 0;
    }

    // Return an instance object to execute SB, and record in a list
    public Instance getInstance(String input, String script, String output) throws java.io.IOException{
        System.out.println("STUB: getInstance in MacPlatform");
        return null;
    }

}



