package com.am.call.dispatcher;

import com.am.call.model.Call;
import com.am.call.model.Employee;
import junit.framework.Assert;
import junit.framework.TestCase;

import java.util.concurrent.LinkedBlockingQueue;

import static org.mockito.Mockito.*;


public class EmployeeRunnableTest extends TestCase {

    public void testRun() throws InterruptedException {
        Employee mockEmployee = mock(Employee.class);
        LinkedBlockingQueue<Call> callQueue = mock(LinkedBlockingQueue.class);
        when(callQueue.take()).thenReturn(mock(Call.class)).thenThrow(new InterruptedException());

        EmployeeRunnable employeeRunnable = new EmployeeRunnable(mockEmployee, callQueue);
        employeeRunnable.run();

        Assert.assertTrue(Thread.interrupted());
        verify(callQueue, times(2)).take();
        verify(mockEmployee, times(1)).takeCall(any(Call.class));
    }

    public void testRunNotRun() throws InterruptedException {
        Employee mockEmployee = mock(Employee.class);
        LinkedBlockingQueue<Call> callQueue = mock(LinkedBlockingQueue.class);

        EmployeeRunnable employeeRunnable = new EmployeeRunnable(mockEmployee, callQueue);
        Thread.currentThread().interrupt();
        employeeRunnable.run();

        Assert.assertTrue(Thread.interrupted());
        verify(callQueue, times(0)).take();
        verify(mockEmployee, times(0)).takeCall(any(Call.class));
    }

}
