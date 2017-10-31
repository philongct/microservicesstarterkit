package lnguyen.taskexecutor;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class NamedThreadFactory implements ThreadFactory {

    private static final ThreadFactory DEFAULT_FACTORY = Executors.defaultThreadFactory();

    private String threadPrefix;

    public NamedThreadFactory(String threadPrefix) {
        this.threadPrefix = threadPrefix;
    }

    @Override
    public Thread newThread(Runnable runnable) {
        Thread t = DEFAULT_FACTORY.newThread(runnable);
        t.setName(threadPrefix + "-" + t.getName().replace("pool-", "p").replace("thread-", "t"));

        return t;
    }
}
