package lnguyen.batch;

import java.util.Map;

import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.launch.NoSuchJobException;

public interface BatchJobManager {
    void launchJob(String jobName, Map<String, JobParameter> params, JobExecutionCallBack resultCallBack) throws NoSuchJobException;
}
