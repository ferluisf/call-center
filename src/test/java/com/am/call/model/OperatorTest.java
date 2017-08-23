package com.am.call.model;

import junit.framework.TestCase;
import static org.mockito.Mockito.*;

public class OperatorTest extends TestCase {

    public void testTakeCall(){
        CallCenter callCenter = mock(CallCenter.class);
        Operator operator = new Operator(1, callCenter);

        Call call = mock(Call.class);
        operator.takeCall(call);

        verify(call, times(1)).process();
        verify(callCenter, times(1)).releaseOperator();
    }
}
