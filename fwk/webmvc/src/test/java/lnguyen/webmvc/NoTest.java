package lnguyen.batch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

import lnguyen.taskexecutor.config.MultiTaskAutoConfig;
import lnguyen.taskexecutor.executor.ExecutorUtils;
import lnguyen.taskexecutor.executor.impl.LoopingTaskExecutor;
import lnguyen.taskexecutor.task.loop.LoopingTaskFuture;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

public class NoTest {
}
