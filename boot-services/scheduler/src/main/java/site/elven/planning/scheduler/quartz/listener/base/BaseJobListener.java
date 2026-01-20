package site.elven.planning.scheduler.quartz.listener.base;

import org.quartz.listeners.JobListenerSupport;
import org.springframework.stereotype.Component;

@Component
public class BaseJobListener extends JobListenerSupport {
    @Override
    public String getName() {
        return "base-job-listener";
    }
}
