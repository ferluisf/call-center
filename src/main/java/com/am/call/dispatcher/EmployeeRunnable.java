package com.am.call.dispatcher;

import com.am.call.model.Call;
import com.am.call.model.Employee;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * It runs waiting for a call to be added to the queue and give the call to the employee to process it
 */
public class EmployeeRunnable implements Runnable {

    private Employee employee;
    private LinkedBlockingQueue<Call> callQueue;

    public EmployeeRunnable(Employee employee, LinkedBlockingQueue<Call> callQueue) {
        this.employee = employee;
        this.callQueue = callQueue;
    }

    public void run() {
        while(!Thread.currentThread().isInterrupted()){
            Call call;
            try {
                call = callQueue.take();
                employee.takeCall(call);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Interrupted while Employee trying to take a call from queue");
            }
        }
    }
}
