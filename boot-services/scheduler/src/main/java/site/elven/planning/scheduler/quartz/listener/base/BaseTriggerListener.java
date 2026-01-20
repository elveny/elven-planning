package site.elven.planning.scheduler.quartz.listener.base;

import org.quartz.listeners.TriggerListenerSupport;
import org.springframework.stereotype.Component;

@Component
public class BaseTriggerListener extends TriggerListenerSupport {
    @Override
    public String getName() {
        return "base-trigger-listener";
    }
}
