package com.example.rpstest2;

import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class PiezoManager {
    private static final String TAG = "PiezoManager";
    private static final File PIEZO_DEVICE_FILE = new File("/dev/fpga_piezo");
    private static char note = 0;

    private static final char NULL_NOTE = 0x00;

    public PiezoManager() {
        Log.d(TAG, "Piezo manager created");
    }

    public static void setNote(char newNote) {
        note = newNote;
        UpdatePiezo();
    }

    public static void resetNote() {
        PiezoManager.setNote(NULL_NOTE);
    }

    public static void playNoteFor(char note, int ms) {
        setNote(note);
        UpdatePiezo();
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        resetNote();
    }


    private static void UpdatePiezo() {
        Log.d(TAG, "Updating piezo");
        try (FileOutputStream outputStream = new FileOutputStream(PIEZO_DEVICE_FILE)) {
            outputStream.write(note);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void UserWin(){
        PiezoManager.playNoteFor((char) 39, 100);
        PiezoManager.resetNote();
    }
    static void ComputerWin(){
        PiezoManager.playNoteFor((char) 1, 100);
        PiezoManager.resetNote();
    }
    static void Draw(){
        PiezoManager.playNoteFor((char) 53, 100);
        PiezoManager.resetNote();
    }
}