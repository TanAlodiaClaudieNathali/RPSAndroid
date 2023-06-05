package com.example.rpstest2;

import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class LEDManager {
    public static final int LED_COUNT = 8;

    private static final String TAG = "LEDManager";
    private static final byte[] LED_buffer = new byte[LED_COUNT];
    private static final File LED_DEVICE_FILE = new File("/dev/fpga_led");


    public LEDManager() {
        //set all LEDs to off
        for (int i = 0; i < LED_COUNT; i++) {
            LED_buffer[i] = 0;
        }
        UpdateLEDs();
        Log.d(TAG, "LED manager created");
    }

    public static void LED_ON(int led) {
        if (led > 0 && led < LED_COUNT) {
            LED_buffer[led - 1] = 1;
        } else {
            Log.e(TAG, "LED_ON: invalid LED number");
        }
        UpdateLEDs();
    }

    public static void LED_OFF(int led) {
        if (led > 0 && led < LED_COUNT) {
            LED_buffer[led - 1] = 0;
        } else {
            Log.e(TAG, "LED_OFF: invalid LED number");
        }
        UpdateLEDs();
    }

    private static void UpdateLEDs() {
        Log.d(TAG, "Updating LEDs");
        try (FileOutputStream outputStream = new FileOutputStream(LED_DEVICE_FILE)) {
            outputStream.write(LED_buffer);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

