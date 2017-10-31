package lnguyen.taskexecutor.config;

import lnguyen.taskexecutor.executor.ExecutorUtils;
import lnguyen.taskexecutor.executor.impl.ExtendedConcurrentTaskExecutor;
import lnguyen.taskexecutor.executor.impl.LoopingTaskExecutor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;

@Configuration
public class MultiTaskAutoConfig {

    @Bean(destroyMethod = "terminate")
    @Primary
    @Lazy
    public LoopingTaskExecutor loopingTaskExecutor() {
        return new LoopingTaskExecutor(new ExtendedConcurrentTaskExecutor(ExecutorUtils.newCachedThreadPool(60, "loop")));
    }
}
