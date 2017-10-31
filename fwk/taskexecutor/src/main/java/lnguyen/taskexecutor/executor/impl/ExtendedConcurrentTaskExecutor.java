package lnguyen.taskexecutor.executor.impl;

import java.util.concurrent.ThreadPoolExecutor;

import lnguyen.taskexecutor.executor.ExtendedTaskExecutor;
import lnguyen.taskexecutor.task.Task;
import lnguyen.taskexecutor.task.TaskErrorHandler;
import lnguyen.taskexecutor.task.TaskWaiter;
import lnguyen.taskexecutor.task.impl.SimpleTaskWaiter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ConcurrentTaskExecutor;

public class ExtendedConcurrentTaskExecutor extends ConcurrentTaskExecutor implements ExtendedTaskExecutor {

    private static final Logger logger = LoggerFactory.getLogger(ExtendedConcurrentTaskExecutor.class);

    public ExtendedConcurrentTaskExecutor(ThreadPoolExecutor executor) {
        super(executor);
    }

    @Override
    public TaskWaiter execute(Task task, TaskErrorHandler errorHandler) {
        SimpleTaskWaiter waiter = new SimpleTaskWaiter();
        this.execute(() -> {
            try {
                task.run();
            } catch (Exception e) {
                if (errorHandler != null) {
                    errorHandler.handle(e);
                } else if (logger.isWarnEnabled()){
                    logger.warn("Task execution exception", e);
                }
            } finally {
                waiter.finnish();
            }
        });

        return waiter;
    }

    @Override
    public void shutdown() {
        ((ThreadPoolExecutor) getConcurrentExecutor()).shutdown();
    }

    @Override
    public void terminate() {
        ((ThreadPoolExecutor) getConcurrentExecutor()).shutdownNow();
    }
}
