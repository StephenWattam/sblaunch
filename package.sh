#!/bin/sh

jar -cvfm SBLaunch.jar manifest.txt resources/* com/stephenwattam/sblaunch/*.class com/stephenwattam/sblaunch/*/*.class
