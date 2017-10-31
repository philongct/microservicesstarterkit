package lnguyen.taskexecutor.task.loop;

import lnguyen.taskexecutor.executor.TaskExecutorService;
import lnguyen.taskexecutor.executor.impl.LoopingTaskExecutor;
import lnguyen.taskexecutor.task.TaskWaiter;
import lnguyen.taskexecutor.task.impl.SimpleTaskWaiter;

public class LoopingTaskFuture extends SimpleTaskWaiter {
    private LoopingTask task;

    public LoopingTaskFuture(LoopingTask task) {
        this.task = task;
    }

    public void stop() {
        this.task.stop();
    }
}
