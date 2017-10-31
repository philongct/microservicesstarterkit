package lnguyen.taskexecutor.executor;

import lnguyen.taskexecutor.task.Task;
import lnguyen.taskexecutor.task.TaskErrorHandler;
import lnguyen.taskexecutor.task.TaskWaiter;

public interface TaskExecutorService {
    TaskWaiter execute(Task task, TaskErrorHandler errorHandler);

    void shutdown();

    void terminate();
}
