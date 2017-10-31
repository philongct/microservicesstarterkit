package lnguyen.taskexecutor;

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

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MultiTaskAutoConfig.class)
public class LoopingTaskExecutorTest {

    @Autowired
    private LoopingTaskExecutor loopingTaskExecutor;

    @Test(timeout = 500)
    public void test() throws InterruptedException {
        StringBuilder catchedErrs = new StringBuilder();

        CountDownLatch latch1 = new CountDownLatch(10);
        CountDownLatch latch2 = new CountDownLatch(15);
        CountDownLatch latch3 = new CountDownLatch(15);

        LoopingTaskFuture f1 = loopingTaskExecutor.execute(() -> {
            latch1.countDown();
        }, 5, null);

        LoopingTaskFuture f2 = loopingTaskExecutor.execute(() -> {
            latch2.countDown();

            if (latch2.getCount() == 7 || latch2.getCount() == 3) {
                throw new RuntimeException("errror");
            }
        }, 5, (e) -> {
            catchedErrs.append(e.getLocalizedMessage());
        });

        LoopingTaskFuture f3 = loopingTaskExecutor.execute(() -> {
            latch3.countDown();
            if (latch3.getCount() == 2) {
                // just to see log
                throw new RuntimeException("stupid");
            }
        }, 5, null);

        latch1.await();
        latch2.await();
        latch3.await();

        f1.stop();
        f2.stop();
        f3.stop();

        long fcount1 = latch1.getCount();
        long fcount2 = latch2.getCount();
        long fcount3 = latch3.getCount();

        // wait a little to make sure our task should stop
        Thread.sleep(20);
        assertEquals(fcount1, latch1.getCount());
        assertEquals(fcount2, latch2.getCount());
        assertEquals(fcount3, latch3.getCount());
        assertEquals("errrorerrror", catchedErrs.toString());
    }
}
