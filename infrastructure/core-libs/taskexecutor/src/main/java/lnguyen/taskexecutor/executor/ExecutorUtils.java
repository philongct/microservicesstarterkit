package lnguyen.taskexecutor.executor;

import java.util.concurrent.*;

import lnguyen.taskexecutor.NamedThreadFactory;
import lnguyen.taskexecutor.executor.impl.ExtendedConcurrentTaskExecutor;

public class ExecutorUtils {
    public static ThreadPoolExecutor newBoundedThreadPool(int maxThreads, int threadAliveTimeout, int queueSize, String threadPrefix) {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(maxThreads, maxThreads,
                threadAliveTimeout, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(queueSize > 0 ? queueSize : Integer.MAX_VALUE),
                new NamedThreadFactory(threadPrefix));

        executor.allowCoreThreadTimeOut(true);

        return executor;
    }

    public static ScheduledThreadPoolExecutor newScheduledExecutor(int coreSize) {
        return new ScheduledThreadPoolExecutor(coreSize, new NamedThreadFactory("sched"));
    }

    public static ThreadPoolExecutor newCachedThreadPool(int threadTimeout, String threadPrefix) {
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newCachedThreadPool(new NamedThreadFactory(threadPrefix));
        executor.setKeepAliveTime(threadTimeout, TimeUnit.SECONDS);

        return executor;
    }

    /**
     * Create new concurrent task executor
     *
     * @param concurrentThreads
     * @param threadAliveTimeout
     * @param threadPrefix
     *
     * @return Custom Spring ConcurrentTaskExecutor
     */
    public static ExtendedConcurrentTaskExecutor newConcurrentTaskExecutor(int concurrentThreads, int threadAliveTimeout, String threadPrefix) {
        ThreadPoolExecutor threadPoolExecutor = newBoundedThreadPool(concurrentThreads, threadAliveTimeout, Integer.MAX_VALUE, threadPrefix);
        return new ExtendedConcurrentTaskExecutor(threadPoolExecutor);
    }
}
