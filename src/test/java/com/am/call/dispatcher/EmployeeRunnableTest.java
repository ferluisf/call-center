package com.am.call.dispatcher;

import com.am.call.model.Call;
import com.am.call.model.CallWrapper;
import com.am.call.model.Employee;
import junit.framework.Assert;
import junit.framework.TestCase;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.concurrent.LinkedBlockingQueue;

import static org.mockito.Mockito.*;


public class EmployeeRunnableTest extends TestCase {

    public void testRun() throws InterruptedException {
        Employee mockEmployee = mock(Employee.class);
        doAnswer(new Answer<Void>() {
            public Void answer(InvocationOnMock invocationOnMock) throws Throwable {
                Call call = invocationOnMock.getArgument(0);
                call.process();
                return null;
            }
        }).when(mockEmployee).takeCall(any(Call.class));

        LinkedBlockingQueue<Call> callQueue = new LinkedBlockingQueue<Call>();

        EmployeeRunnable employeeRunnable = new EmployeeRunnable(mockEmployee, callQueue);
        Thread thread = new Thread(employeeRunnable);
        thread.start();

        CallWrapper call = new CallWrapper(1);
        callQueue.put(call);
        call.awaitFinished();

        thread.interrupt();
        thread.join(15000);

        verify(mockEmployee, times(1)).takeCall(call);
        Assert.assertFalse(thread.isAlive());
    }
}
