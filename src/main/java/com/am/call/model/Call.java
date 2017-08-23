package com.am.call.model;

import java.util.Random;

/**
 * Represents the call to be processed
 */
public class Call {

    private long id;

    public Call(long id){
        this.id = id;
    }

    public void process() {
        int durationInSeconds = 5 + new Random().nextInt(6);
        try {
            Thread.sleep(durationInSeconds * 1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Interrupted call " + this.getId() + " in process");
        }
    }


    public long getId() {
        return id;
    }
}
