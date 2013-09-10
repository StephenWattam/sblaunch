
package com.stephenwattam.sblaunch.platform;

public class WindowsPlatform extends Platform{

    public void checkRubyVersion(){
        System.out.println("STUB: checkRubyVersion in WindowsPlatform");
    }

    public void checkGemVersion(){
        System.out.println("STUB: checkGemVersion in WindowsPlatform");
    }


    public int countInstances(){
        System.out.println("STUB: countInstances in WindowsPlatform");
        return 0;
    }

    // Return an instance object to execute SB, and record in a list
    public Instance getInstance(String input, String script, String output) throws java.io.IOException{
        System.out.println("STUB: getInstance in WindowsPlatform");
        return null;
    }
}

