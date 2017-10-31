package lnguyen.taskexecutor.executor.impl;

import java.util.ArrayList;
import java.util.List;

import lnguyen.taskexecutor.executor.TaskExecutorService;
import lnguyen.taskexecutor.task.Task;
import lnguyen.taskexecutor.task.TaskErrorHandler;
import lnguyen.taskexecutor.task.TaskWaiter;
import lnguyen.taskexecutor.task.loop.LoopingTask;
import lnguyen.taskexecutor.task.loop.LoopingTaskFuture;

public class LoopingTaskExecutor implements TaskExecutorService {

    private TaskExecutorService taskExecutor;

    private List<LoopingTaskFuture> runningTasks = new ArrayList<>();

    public LoopingTaskExecutor(TaskExecutorService executorService) {
        this.taskExecutor = executorService;
    }

    public LoopingTaskFuture execute(Task task, int sleepMillis, TaskErrorHandler errorHandler) {
        LoopingTask loopingTask = new LoopingTask(task, sleepMillis, errorHandler, taskExecutor);
        LoopingTaskFuture future = new LoopingTaskFuture(loopingTask);
        runningTasks.add(future);

        taskExecutor.execute(loopingTask, errorHandler);
        return future;
    }

    @Override
    public TaskWaiter execute(Task task, TaskErrorHandler errorHandler) {
        return execute(task, 10, errorHandler);
    }

    @Override
    public void shutdown() {
        runningTasks.forEach(LoopingTaskFuture::stop);
    }

    @Override
    public void terminate() {
        runningTasks.forEach(LoopingTaskFuture::stop);
    }
}
