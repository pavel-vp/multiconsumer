package com.example.mc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Parser class for the parsing input file with messages
 */
public class Parser {
    final private String fileName;
    final private Producer producer;

    /**
     * Creates a Parser with a given producer and a file
     * @param producer
     * @param fileName
     */
    public Parser(Producer producer, String fileName) {
        this.producer = producer;
        this.fileName = fileName;
    }

    /**
     * Proceeding the file line by line. Call producers "handle" on each line and "finish" at the end
     * @throws Exception
     */
    public void proceed() throws Exception {
        try(BufferedReader reader = new BufferedReader(new FileReader(fileName))) {

            TimeUtil.setStart();
            String line;
            while ((line = reader.readLine()) != null) {
                producer.handle(parseLine(line));
            }
            producer.finish();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private Record parseLine(String line) {
        String s[] = line.split("\\|", -1);
        return new Record(s[0], Long.parseLong(s[1]), s[2]);
    }
}
