package com.example.rpstest2;

public class TextLCD {
    static {
        System.loadLibrary("rpstest2");
    }

    void UserWin(){
        initialize();
        clear();
        print1Line("CONGRATULATIONS!");
        print2Line("YOU WIN!");
    }

    void ComputerWin(){
        initialize();
        clear();
        print1Line("OH NO!");
        print2Line("COMPUTER WIN!");
    }

    void Draw(){
        initialize();
        clear();
        print1Line("DRAW!");
    }
    public native void on();
    public native void off();
    public native void initialize();
    public native void clear();

    public native void print1Line(String str);
    public native void print2Line(String str);

}

