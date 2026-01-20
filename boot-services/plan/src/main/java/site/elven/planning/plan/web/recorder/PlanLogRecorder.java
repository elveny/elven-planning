package site.elven.planning.plan.web.recorder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import site.elven.recording.recorder.AbstractRecorder;
import site.elven.recording.recorder.Record;

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
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
