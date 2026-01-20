package site.elven.planning.plan.web.dal.task;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import site.elven.planning.boot.base.web.dal.base.BaseRepository;
import site.elven.planning.plan.web.model.entity.task.TaskInfo;

import java.util.List;

@Repository
public interface TaskInfoRepository extends BaseRepository<TaskInfo, Long> {
    @Query("SELECT e FROM #{#entityName} e WHERE e.parentId = :parentId AND e.deleted = false")
    List<TaskInfo> findAllByParentId(Long parentId);
}
