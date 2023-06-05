package com.example.rpstest2;

public class FullLED {
    static {
        System.loadLibrary("rpstest2");
    }

    public native int FLEDCONTROL(int ledNum, int red, int green, int blue);
}
