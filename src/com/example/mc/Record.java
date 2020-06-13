package com.example.mc;

/**
 * A simple record data structure to handle with consumers
 */
public class Record {
    final private String id;
    final private long duration;
    final private String payload;
    final private boolean finish;

    /**
     * Create a record
     * @param id
     * @param duration
     * @param payload
     */
    public Record(String id, long duration, String payload) {
        this.id = id;
        this.duration = duration;
        this.payload = payload;
        this.finish = false;
    }

    /**
     * Create a FINISH record
     * @param finish
     */
    public Record(boolean finish) {
        this.id = null;
        this.duration = 0l;
        this.payload = null;
        this.finish = finish;
    }

    public String getId() {
        return id;
    }

    public long getDuration() {
        return duration;
    }

    public String getPayload() {
        return payload;
    }


    public boolean isFinish() {
        return finish;
    }

    @Override
    public String toString() {
        return id + '|' +
                duration + '|' +
                payload;
    }
}
