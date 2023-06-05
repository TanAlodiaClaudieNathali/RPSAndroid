package com.example.rpstest2;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.util.Log;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Calendar;

public class SegmentManager {
    private static final String TAG = "SegmentManager";
    private static final File SEGMENT_DEVICE_FILE = new File("/dev/fpga_segment");
    private static final int NB_TILES = 6;
    private static byte[] segments = new byte[]{48,48,48,48,48,48, 2};
    private static final byte[] reset_segments = new byte[]{48,48,48,48,48,48, 0};
    private static boolean isRunning = false;

    public static void writeToDisplay() {
        while (isRunning) {
            UpdateSegments();
        }
    }

    public static void launchDisplay() {
        isRunning = true;
        Thread t = new Thread(SegmentManager::writeToDisplay);
        t.start();
    }

    public static void stopDisplay() {
        isRunning = false;
    }

    public static void setSegmentAt(int index, byte value) {
        if (index > 0 && index < NB_TILES) {
            segments[index] = value;
        } else {
            Log.e(TAG, "setSegmentAt: invalid index");
        }
    }

    public static void setSegments(byte[] newNums) {
        if (newNums.length == NB_TILES) {
            segments = newNums;
        } else {
            Log.e(TAG, "setSegments: invalid number of segments");
        }
    }

    public static void resetSegments() {
        segments = reset_segments;
    }

    private static void UpdateSegments() {
        try (FileOutputStream outputStream = new FileOutputStream(SEGMENT_DEVICE_FILE)) {
            outputStream.write(segments);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}