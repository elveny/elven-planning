package site.elven.planning.scheduler.quartz.listener.defaults;

import org.springframework.stereotype.Component;
import site.elven.planning.scheduler.quartz.listener.base.BaseJobListener;

@Component
public class DefaultJobListener extends BaseJobListener {
    @Override
    public String getName() {
        return "default-job-listener";
    }
}
