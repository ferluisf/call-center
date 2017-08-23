package com.am.call.model;

import junit.framework.TestCase;

public class CallTest extends TestCase {

    public void testProcess() {
        Call call = new Call(1);
        call.process();
    }

    public void testProcessInterrupted() {
        Call call = new Call(1);
        Thread.currentThread().interrupt();
        call.process();
    }
}
