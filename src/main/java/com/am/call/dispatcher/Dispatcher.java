package com.am.call.dispatcher;

import com.am.call.model.Call;
import com.am.call.model.CallCenter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Main dispatcher. It dispatchs a DispatcherTask to an executor for every call that arrives.
 */
public class Dispatcher {

    public static final int N_THREADS = 10;

    private ExecutorService executor = Executors.newFixedThreadPool(N_THREADS);
    private CallCenter callCenter;

    public Dispatcher(int numberOfOperators, int numberOfSupervisors, int numberOfDirectors){
        callCenter = new CallCenter(numberOfOperators, numberOfSupervisors, numberOfDirectors);
    }

    public void dispatchCall(Call call){
        executor.submit(new DispatcherTask(call, callCenter));
        System.out.println("New call " + call.getId() + " dispatched");
    }
}
