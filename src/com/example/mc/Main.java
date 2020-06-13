package com.example.mc;

public class Main {

    public static void main(String[] args) throws Exception {
        Parser parser = new Parser(new Producer(Integer.parseInt(args[0])), args[1]);
        parser.proceed();
    }
}
