package com.example.mc;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Consumer class, that implements consuming logic of each record
 */
public class RecordConsumer implements Runnable {

    private final AtomicLong totalDuration = new AtomicLong(0l);
    private final Map<String, Integer> map = new HashMap<>();

    private final BlockingQueue<Record> queue = new LinkedBlockingDeque<>();

    /**
     * Submits the record to this consumer for later consuming
     * @param record
     */
    public void submit(Record record) {
        try {
            onAddRecord(record);
            queue.put(record);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private synchronized void onAddRecord(Record record) {
        this.totalDuration.addAndGet(record.getDuration());
        Integer cnt = map.get(record.getId());
        if (cnt == null) {
            cnt = 0;
        }
        map.put(record.getId(), cnt + 1);
    }

    private synchronized void afterProceedRecord(Record record) {
        this.totalDuration.addAndGet(0-record.getDuration());
        Integer cnt = map.get(record.getId());
        if (cnt == 1) {
            map.remove(record.getId());
        } else {
            map.put(record.getId(), cnt - 1);
        }
    }

    @Override
    public void run() {
        try {
            while (true) {
                Record record = queue.take();
                if (record.isFinish()) {
                    break;
                }
                process(record);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void process(Record record) throws InterruptedException {
        long msStart = TimeUtil.getCurrentTimeInMS();
        Thread.sleep(record.getDuration());
        long msEnd = TimeUtil.getCurrentTimeInMS();
        System.out.println(String.format("%20s;\t%s;\tStart:%d;\tEnd:%d;", record.toString(), Thread.currentThread().getName(), msStart, msEnd));
        afterProceedRecord(record);
    }

    /**
     * Method checks if it contains such ID in this consumer now
     * @param id
     * @return
     */
    public synchronized boolean isContainID(String id) {
        return map.containsKey(id);
    }

    /**
     * Method gets the total length of queue of consumers requests (total time)
     * @return
     */
    public long getTotalCurrentDuration() {
        return this.totalDuration.get();
    }
}
