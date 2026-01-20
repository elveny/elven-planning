package site.elven.planning.scheduler.quartz.listener.defaults;

import org.springframework.stereotype.Component;
import site.elven.planning.scheduler.quartz.listener.base.BaseTriggerListener;

@Component
public class DefaultTriggerListener extends BaseTriggerListener {
    @Override
    public String getName() {
        return "default-trigger-listener";
    }
}
