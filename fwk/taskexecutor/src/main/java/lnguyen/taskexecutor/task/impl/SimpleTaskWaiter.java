package lnguyen.taskexecutor.task.impl;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import lnguyen.taskexecutor.task.TaskWaiter;

public class SimpleTaskWaiter implements TaskWaiter {

    private final CountDownLatch doneFlag = new CountDownLatch(1);

    @Override
    public void waitUntilDone() throws InterruptedException {
        doneFlag.await();
    }

    @Override
    public void waitUntil(long timeOut) throws InterruptedException {
        doneFlag.await(timeOut, TimeUnit.MILLISECONDS);
    }

    public void finnish() {
        doneFlag.countDown();
    }
}
