package lnguyen.taskexecutor.config;

import javax.annotation.PreDestroy;
import java.util.concurrent.ScheduledExecutorService;

import lnguyen.taskexecutor.executor.ExecutorUtils;
import lnguyen.taskexecutor.executor.impl.ExtendedConcurrentTaskExecutor;
import lnguyen.taskexecutor.executor.impl.LoopingTaskExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.ScheduledAnnotationBeanPostProcessor;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;

@Configuration
public class SpringVertxAutoConfig {

    @Bean(destroyMethod = "terminate")
    @Primary
    @Lazy
    public LoopingTaskExecutor loopingTaskExecutor() {
        return new LoopingTaskExecutor(new ExtendedConcurrentTaskExecutor(ExecutorUtils.newCachedThreadPool(60, "loop")));
    }
}
