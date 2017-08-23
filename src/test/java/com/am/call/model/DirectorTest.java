package com.am.call.model;

import junit.framework.TestCase;

import static org.mockito.Mockito.*;

public class DirectorTest extends TestCase {

    public void testTakeCall(){
        CallCenter callCenter = mock(CallCenter.class);
        Director director = new Director(1, callCenter);

        Call call = mock(Call.class);
        director.takeCall(call);

        verify(call, times(1)).process();
        verify(callCenter, times(1)).releaseDirector();
    }
}
