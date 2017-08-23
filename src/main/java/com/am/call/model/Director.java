package com.am.call.model;

/**
 * Director employee
 */
public class Director extends AbstractEmployee {

    public Director(long id, CallCenter callCenter) {
        super(id, callCenter);
    }

    public void takeCall(Call call) {
        System.out.println("Director " + this.getId() + " take call " + call.getId());
        call.process();
        this.getCallCenter().releaseDirector();
    }
}
