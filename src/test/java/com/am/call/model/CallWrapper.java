package com.am.call.model;

import java.util.concurrent.CountDownLatch;

public class CallWrapper extends Call{

    private CountDownLatch finishLatch = new CountDownLatch(1);

    public CallWrapper(long id){
        super(id);
    }

    @Override
    public void process() {
        super.process();
        finish();
    }

    private void finish() {
        finishLatch.countDown();
    }

    public void awaitFinished() throws InterruptedException {
        finishLatch.await();
    }

    public boolean isFinished() {
        return finishLatch.getCount() == 0;
    }

}
