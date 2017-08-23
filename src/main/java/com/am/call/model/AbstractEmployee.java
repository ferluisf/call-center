package com.am.call.model;

/**
 * Abstract employee with an id
 */
public abstract class AbstractEmployee implements Employee {

    private long id;
    private CallCenter callCenter;

    public AbstractEmployee(long id, CallCenter callCenter){
        this.id = id;
        this.callCenter = callCenter;
    }

    public long getId() {
        return id;
    }

    public CallCenter getCallCenter() {
        return callCenter;
    }
}
