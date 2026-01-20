package site.elven.planning.scheduler.web.model.entity.task;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Filter;
import site.elven.planning.boot.base.web.model.entity.base.BaseEntity;

import java.io.Serial;
import java.time.LocalDateTime;

@Entity
@Table(name = "scheduler_task_record")
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Filter(name = "delete_filter", condition = "deleted = :deleted")
public class TaskRecord extends BaseEntity {
    @Serial
    private static final long serialVersionUID = 1L;

    @Column(name = "task_id")
    private Long taskId;
    @Column(name = "scheduled_fire_time")
    private LocalDateTime scheduledFireTime;
    @Column(name = "fire_time")
    private LocalDateTime fireTime;
    @Column(name = "delay_time")
    private Long delayTime;
    @Column(name = "job_run_time")
    private Long jobRunTime;
    @Column(name = "previous_fire_time")
    private LocalDateTime previousFireTime;
    @Column(name = "next_fire_time")
    private LocalDateTime nextFireTime;
    @Column(name = "vetoed")
    private Boolean vetoed;
    @Column(name = "is_error")
    private Boolean isError;
    @Column(name = "error_message", length = 1024)
    private String errorMessage;

}
