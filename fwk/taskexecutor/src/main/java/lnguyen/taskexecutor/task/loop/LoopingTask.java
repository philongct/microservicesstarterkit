package lnguyen.taskexecutor.task.loop;

import java.util.concurrent.ExecutorService;

import lnguyen.taskexecutor.executor.TaskExecutorService;
import lnguyen.taskexecutor.task.Task;
import lnguyen.taskexecutor.task.TaskErrorHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoopingTask implements Task {

    private static final Logger log = LoggerFactory.getLogger(LoopingTask.class);

    private Task actualTask;

    private TaskErrorHandler errorHandler;

    private int sleepInterval;

    private int executionCount = 0;

    private TaskExecutorService executorService;

    private volatile boolean stop = false;

    public LoopingTask(Task task, int sleepInterval, TaskErrorHandler errorHandler, TaskExecutorService executorService) {
        this.actualTask = task;
        this.sleepInterval = sleepInterval;
        this.errorHandler = errorHandler;

        this.executorService = executorService;
    }

    @Override
    public void run() {
        while(!stop) {
            try {
                if (sleepInterval > 0) {
                    Thread.sleep(sleepInterval);
                }
                ++executionCount;
                actualTask.run();
            } catch (Exception e) {
                if (errorHandler != null) {
                    errorHandler.handle(e);
                } else if (log.isWarnEnabled()) {
                    log.warn("Error at execution {}th", executionCount, e);
                }
            }
        }
    }

    public void stop() {
        stop = true;
    }
}
