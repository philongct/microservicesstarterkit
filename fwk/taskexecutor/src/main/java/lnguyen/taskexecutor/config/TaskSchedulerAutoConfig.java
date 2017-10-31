package lnguyen.taskexecutor.config;

import javax.annotation.PreDestroy;
import java.util.concurrent.ScheduledExecutorService;

import lnguyen.taskexecutor.executor.ExecutorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.ScheduledAnnotationBeanPostProcessor;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;

@Configuration
@EnableScheduling
public class TaskSchedulerAutoConfig {

    private ScheduledExecutorService concurrentScheduler;

    @Autowired
    private ScheduledAnnotationBeanPostProcessor scheduledBeanProcessor;

    @Bean
    @Primary
    @Lazy
    public TaskScheduler taskScheduler() {
        return new ConcurrentTaskScheduler(ExecutorUtils.newScheduledExecutor(8));
    }

    @PreDestroy
    public void destroy() {
        scheduledBeanProcessor.destroy();
        if (concurrentScheduler != null) {
            concurrentScheduler.shutdownNow();
        }
    }
}
