package com.am.call.model;

import junit.framework.Assert;
import junit.framework.TestCase;

public class CallCenterTest extends TestCase {

    public void testCallCenter() throws InterruptedException {
        int numberOfOperators = 2;
        int numberOfSupervisors = 1;
        int numberOfDirectors = 1;
        int totalEmployees = numberOfOperators + numberOfSupervisors + numberOfDirectors;

        CallCenter callCenter = new CallCenter(numberOfOperators, numberOfSupervisors, numberOfDirectors);

        Assert.assertEquals(totalEmployees, callCenter.getNumberOfFreeEmployees());
        CallWrapper call = new CallWrapper(1);
        callCenter.waitFreeEmployeeAndDispatchCall(call);
        call.awaitFinished();
    }
}
