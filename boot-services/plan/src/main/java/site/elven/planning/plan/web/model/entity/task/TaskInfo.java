package site.elven.planning.plan.web.model.entity.task;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import site.elven.planning.boot.base.web.model.entity.base.BaseEntity;
import site.elven.planning.api.common.enums.plan.TaskMode;
import site.elven.planning.api.common.enums.plan.TaskStatus;

@Entity
@Table(name = "plan_task_info")
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskInfo extends BaseEntity {
    @Column(name = "username", length = 64)
    private String username;
    @Column(name = "title", length = 128)
    private String title;
    @Column(name = "description", length = 1024)
    private String description;
    @Column(name = "mode", length = 32)
    @Enumerated(EnumType.STRING)
    private TaskMode mode;
    @Column(name = "priority")
    private Integer priority;
    @Column(name = "status", length = 32)
    @Enumerated(EnumType.STRING)
    private TaskStatus status;
    @Column(name = "is_public")
    private Boolean isPublic;
    @Column(name = "template_id")
    private Long templateId;
    @Column(name = "parent_id")
    private Long parentId;
}
