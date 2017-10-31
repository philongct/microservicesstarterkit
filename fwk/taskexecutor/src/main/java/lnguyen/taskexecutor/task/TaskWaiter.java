package lnguyen.taskexecutor.task;

public interface TaskWaiter {
    void waitUntilDone() throws InterruptedException;

    void waitUntil(long timeOut) throws InterruptedException;
}
