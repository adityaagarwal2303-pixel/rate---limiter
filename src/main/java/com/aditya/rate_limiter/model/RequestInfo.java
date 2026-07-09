package com.aditya.rate_limiter.model;

public class RequestInfo {

    private int count;
    private long windowStart;

    public RequestInfo(int count, long windowStart) {
        this.count = count;
        this.windowStart = windowStart;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public long getWindowStart() {
        return windowStart;
    }

    public void setWindowStart(long windowStart) {
        this.windowStart = windowStart;
    }
}