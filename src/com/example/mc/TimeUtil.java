package com.example.mc;

/**
 * Just time utility
 */
public class TimeUtil {
    private static volatile long start;

    public static long getCurrentTimeInMS() {
        return System.currentTimeMillis() - start;
    }

    public static void setStart() {
        start = System.currentTimeMillis();
    }

}
