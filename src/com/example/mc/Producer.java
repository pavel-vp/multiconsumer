package com.example.mc;

/**
 * A Producer class to handle a Record and pass it to the best consumer
 */
public class Producer {
    final private RecordConsumer[] consumerList;

    /**
     * Create a Producer with a number of consumers
     * @param numOfConsumers
     */
    public Producer(int numOfConsumers) {
        this.consumerList = new RecordConsumer[numOfConsumers];
        for (int i = 0; i < numOfConsumers; i++) {
            consumerList[i] = new RecordConsumer();
            new Thread(consumerList[i]).start();
        }
    }

    /**
     *  Handle a record. Pass it to the best consumer.
     * @param record
     * @throws InterruptedException
     */
    public void handle(Record record) throws InterruptedException {
        if (record.getId() == null || "".equals(record.getId())) {
            // wait
            Thread.sleep(record.getDuration());
        } else {
            RecordConsumer recordConsumer = findBestFit(record.getId());
            recordConsumer.submit(record);
        }
    }

    /**
     * Internal method to find best consumer.
     * @param id A record ID
     * @return a Best found consumer
     */
    private RecordConsumer findBestFit(String id) {
        // find consumer by this condition:
        // - one who has same ID
        // - OR one with minimal duration total (current)
        RecordConsumer found = null;
        long minDuration = Long.MAX_VALUE;
        for (RecordConsumer consumer : consumerList) {
            if (consumer.isContainID(id)) {
                return consumer;
            }
            if (consumer.getTotalCurrentDuration() < minDuration) {
                minDuration = consumer.getTotalCurrentDuration();
                found = consumer;
            }
        }
        return found;
    }

    /**
     * Method sends finish signal to all consumers
     */
    public void finish() {
        for (RecordConsumer consumer : consumerList) {
            consumer.submit(new Record(true));
        }
    }
}
