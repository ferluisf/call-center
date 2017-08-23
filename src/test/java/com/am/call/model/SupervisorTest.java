package com.am.call.model;

import junit.framework.TestCase;

import static org.mockito.Mockito.*;

public class SupervisorTest extends TestCase {

    public void testTakeCall(){
        CallCenter callCenter = mock(CallCenter.class);
        Supervisor supervisor = new Supervisor(1, callCenter);

        Call call = mock(Call.class);
        supervisor.takeCall(call);

        verify(call, times(1)).process();
        verify(callCenter, times(1)).releaseSupervisor();
    }
}
