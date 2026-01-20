package site.elven.planning.scheduler.web.dal.task;

import org.springframework.stereotype.Repository;
import site.elven.planning.boot.base.web.dal.base.BaseRepository;
import site.elven.planning.scheduler.web.model.entity.task.TaskRecord;

@Repository
public interface TaskRecordRepository extends BaseRepository<TaskRecord, Long> {
}
