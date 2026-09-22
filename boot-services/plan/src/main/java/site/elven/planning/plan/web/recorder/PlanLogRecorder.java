package site.elven.planning.plan.web.recorder;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import site.elven.recording.recorder.AbstractRecorder;
import site.elven.recording.recorder.Record;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;

@Slf4j
@Component
public class PlanLogRecorder extends AbstractRecorder {

    private final ObjectMapper objectMapper;

    public PlanLogRecorder(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void record(Record record) {
        try {
            log.info("recording >>>>>>>> {}", objectMapper.writeValueAsString(record));
        } catch (JacksonException e) {
            throw new RuntimeException(e);
        }
    }
}
