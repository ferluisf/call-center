package com.am.call.model;

/**
 * Operator employee
 */
public class Operator extends AbstractEmployee {

    public Operator(long id, CallCenter callCenter){
        super(id, callCenter);
    }

    public void takeCall(Call call) {
        System.out.println("Operator " + this.getId() + " take call " + call.getId());
        call.process();
        this.getCallCenter().releaseOperator();
    }
}
