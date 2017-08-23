package com.am.call.dispatcher;

import com.am.call.model.Call;
import com.am.call.model.CallCenter;
import junit.framework.Assert;
import junit.framework.TestCase;
import static org.mockito.Mockito.*;

public class DispatcherTaskTest extends TestCase {

    public void testRun() throws InterruptedException {
        Call call = mock(Call.class);
        CallCenter callCenter = mock(CallCenter.class);

        DispatcherTask dispatcherTask = new DispatcherTask(call, callCenter);
        dispatcherTask.run();

        verify(callCenter, times(1)).waitFreeEmployeeAndDispatchCall(any(Call.class));
    }

    public void testRunInterrupted() throws InterruptedException {
        Call call = mock(Call.class);

        CallCenter callCenter = mock(CallCenter.class);
        doThrow(new InterruptedException()).when(callCenter).waitFreeEmployeeAndDispatchCall(any(Call.class));

        DispatcherTask dispatcherTask = new DispatcherTask(call, callCenter);
        dispatcherTask.run();

        verify(callCenter, times(1)).waitFreeEmployeeAndDispatchCall(any(Call.class));
        Assert.assertTrue(Thread.interrupted());
    }
}
