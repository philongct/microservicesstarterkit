package lnguyen.batch.config;

import javax.annotation.PreDestroy;
import java.util.concurrent.ScheduledExecutorService;

import lnguyen.taskexecutor.executor.ExecutorUtils;
import lnguyen.taskexecutor.executor.impl.ExtendedConcurrentTaskExecutor;
import lnguyen.taskexecutor.executor.impl.LoopingTaskExecutor;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.*;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.ScheduledAnnotationBeanPostProcessor;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;

@Configuration
@EnableBatchProcessing(modular = true)
@PropertySource(value = "classpath:fwk/batch.properties")
public class BatchAutoConfig {


}
