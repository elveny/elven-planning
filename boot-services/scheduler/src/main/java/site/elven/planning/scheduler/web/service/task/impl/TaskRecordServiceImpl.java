package site.elven.planning.scheduler.web.service.task.impl;

import jakarta.persistence.criteria.Predicate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import site.elven.planning.api.web.model.base.response.page.PageRsp;
import site.elven.planning.api.web.model.scheduler.response.task.TaskRecordRsp;
import site.elven.planning.boot.base.web.service.base.impl.ServiceImpl;
import site.elven.planning.scheduler.web.dal.task.TaskRecordRepository;
import site.elven.planning.scheduler.web.mapstruct.task.TaskRecordMapStruct;
import site.elven.planning.scheduler.web.model.entity.task.TaskRecord;
import site.elven.planning.api.web.model.scheduler.request.task.TaskRecordReq;
import site.elven.planning.scheduler.web.service.task.TaskRecordService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class TaskRecordServiceImpl extends ServiceImpl<TaskRecord, Long, TaskRecordRepository> implements TaskRecordService {
    @Autowired
    private TaskRecordMapStruct taskRecordMapStruct;
    @Override
    public PageRsp<TaskRecordRsp> list(TaskRecordReq taskRecordReq) {

        Specification<TaskRecord> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            Long taskId = taskRecordReq.getTaskId();
            if (taskId != null) {
                predicates.add(cb.equal(root.get("taskId"), taskId));
            }

            Boolean vetoed = taskRecordReq.getVetoed();
            if (vetoed != null) {
                predicates.add(cb.equal(root.get("vetoed"), vetoed));
            }

            Boolean isError = taskRecordReq.getIsError();
            if (isError != null) {
                predicates.add(cb.equal(root.get("isError"), isError));
            }

            String errorMessage = taskRecordReq.getErrorMessage();
            if (errorMessage != null) {
                predicates.add(cb.like(root.get("errorMessage"), "%" + errorMessage + "%"));
            }

            Long delayTimeMin = taskRecordReq.getDelayTimeMin();
            if (delayTimeMin != null) {
                predicates.add(cb.ge(root.get("delayTime"), delayTimeMin));
            }
            Long delayTimeMax = taskRecordReq.getDelayTimeMax();
            if (delayTimeMax != null) {
                predicates.add(cb.le(root.get("delayTime"), delayTimeMax));
            }

            Long jobRunTimeMin = taskRecordReq.getJobRunTimeMin();
            if (jobRunTimeMin != null) {
                predicates.add(cb.ge(root.get("jobRunTime"), jobRunTimeMin));
            }
            Long jobRunTimeMax = taskRecordReq.getJobRunTimeMax();
            if (jobRunTimeMax != null) {
                predicates.add(cb.le(root.get("jobRunTime"), jobRunTimeMax));
            }

            LocalDateTime fireTimeMin = taskRecordReq.getFireTimeMin();
            if (fireTimeMin != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("fireTime"), fireTimeMin));
            }
            LocalDateTime fireTimeMax = taskRecordReq.getFireTimeMax();
            if (fireTimeMax != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("fireTime"), fireTimeMax));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 组装分页信息
        int pageNumber = taskRecordReq.getPageNumber();
        int pageSize = taskRecordReq.getPageSize();

        // 组装排序信息
        Sort sort = Sort.by(Sort.Order.desc("taskId"), Sort.Order.desc("id"));

        // 执行查询
        PageRsp<TaskRecord> pageRsp = list(spec, pageNumber, pageSize, sort);
        return new PageRsp<>(pageRsp.getTotalPages(), pageRsp.getTotalElements(), pageRsp.getNumberOfElements(), taskRecordMapStruct.entity2Rsp(pageRsp.getContent()));
    }
}
