package com.athena.utils;

import java.time.ZonedDateTime;

public class Timer {
    private long startTime = 0;
    private long endTime = 0;

    public void startTimer() {
        startTime = System.currentTimeMillis();
    }

    public void stopTimer() {
        endTime = System.currentTimeMillis();
    }

    public long getElapsedTime() {
        if (endTime == 0) {
            return System.currentTimeMillis() - startTime;
        }
        return endTime - startTime;
    }

    public void resetTimer() {
        this.startTime = 0;
        this.endTime = 0;
    }

    public static ZonedDateTime getCurrentDateTime() {
        return ZonedDateTime.now();
    }
}