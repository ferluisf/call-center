package com.am.call.dispatcher;

import com.am.call.model.Call;
import com.am.call.model.CallCenter;

/**
 * The task Runnable that wait till an employee is free and dispatch the call to it.
 */
public class DispatcherTask implements Runnable {

    private Call call;
    private CallCenter callCenter;


    public DispatcherTask(Call call, CallCenter callCenter) {
        this.call = call;
        this.callCenter = callCenter;
    }

    public void run() {
        try {
            callCenter.waitFreeEmployeeAndDispatchCall(call);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Interrupted when trying to dispatch call " + call.getId());
        }
    }
}
