package com.am.call;

import com.am.call.dispatcher.Dispatcher;
import com.am.call.model.Call;
import com.am.call.model.CallWrapper;
import junit.framework.Assert;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

public class AppTest extends TestCase {

    public void testDispatcherWith4Employees() throws InterruptedException {
        Dispatcher dispatcher = new Dispatcher(2,1,1);

        List<CallWrapper> calls = dispatchNCalls(10, 1, dispatcher);

        int callProcessed = awaitCallsProcessed(calls);
        Assert.assertEquals(10, callProcessed);
    }

    public void testDispatcherWith10Employees() throws InterruptedException {
        Dispatcher dispatcher = new Dispatcher(7,2,1);

        List<CallWrapper> calls = dispatchNCalls(20, 1, dispatcher);

        int callProcessed = awaitCallsProcessed(calls);
        Assert.assertEquals(20, callProcessed);
    }

    public void testDispatcherWith100Employees() throws InterruptedException {
        Dispatcher dispatcher = new Dispatcher(90,8,2);

        List<CallWrapper> calls = dispatchNCalls(80, 1, dispatcher);

        int callProcessed = awaitCallsProcessed(calls);
        Assert.assertEquals(80, callProcessed);

        calls = dispatchNCalls(200, 1, dispatcher);

        callProcessed = awaitCallsProcessed(calls);
        Assert.assertEquals(200, callProcessed);
    }



    private int awaitCallsProcessed(List<CallWrapper> calls) throws InterruptedException {
        int callProcessed = 0;
        for (CallWrapper call : calls) {
            call.awaitFinished();
            callProcessed++;
        }
        return callProcessed;
    }

    private List<CallWrapper> dispatchNCalls(int n, int initId, Dispatcher dispatcher){
        int from = initId;
        int to = initId + n;

        List<CallWrapper> calls = new ArrayList<CallWrapper>();

        for (int i = from; i < to; i++) {
            CallWrapper call = new CallWrapper(i);
            calls.add(call);
            dispatcher.dispatchCall(call);
        }

        return calls;
    }
}
