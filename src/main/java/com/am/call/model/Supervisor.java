package com.am.call.model;

/**
 * Supervisor employee
 */
public class Supervisor extends AbstractEmployee {

    public Supervisor(long id, CallCenter callCenter) {
        super(id, callCenter);
    }

    public void takeCall(Call call) {
        System.out.println("Supervisor " + this.getId() + " take call " + call.getId());
        call.process();
        this.getCallCenter().releaseSupervisor();
    }
}
