-- 定时任务表
CREATE TABLE IF NOT EXISTS scheduler_task_info (
    id BIGSERIAL PRIMARY KEY,
    created_by VARCHAR(64) NOT NULL DEFAULT 'sys',
    updated_by VARCHAR(64) NOT NULL DEFAULT 'sys',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted BOOLEAN DEFAULT FALSE,
    deleted_id BIGINT DEFAULT 0,
    username VARCHAR(64) NOT NULL,
    task_type VARCHAR(16) NOT NULL,
    task_name VARCHAR(128) NOT NULL,
    task_desc VARCHAR(128) NOT NULL,
    task_status VARCHAR(16) NOT NULL,
    group_name VARCHAR(128) NOT NULL,
    job_name VARCHAR(128) NOT NULL,
    trigger_name VARCHAR(128) NOT NULL,
    job_class_name VARCHAR(128) NOT NULL,
    cron_expression VARCHAR(128) NOT NULL,
    enabled BOOLEAN DEFAULT TRUE,
    version INT DEFAULT 0,
    UNIQUE (task_name, deleted_id),
    UNIQUE (group_name, job_name, deleted_id)
);

-- 添加注释
COMMENT ON TABLE scheduler_task_info IS '定时任务表';
COMMENT ON COLUMN scheduler_task_info.id IS '主键ID';
COMMENT ON COLUMN scheduler_task_info.created_by IS '创建人';
COMMENT ON COLUMN scheduler_task_info.updated_by IS '修改人';
COMMENT ON COLUMN scheduler_task_info.created_at IS '创建时间';
COMMENT ON COLUMN scheduler_task_info.updated_at IS '修改时间';
COMMENT ON COLUMN scheduler_task_info.deleted IS '是否删除';
COMMENT ON COLUMN scheduler_task_info.deleted_id IS '删除ID';
COMMENT ON COLUMN scheduler_task_info.username IS '所属用户';
COMMENT ON COLUMN scheduler_task_info.task_type IS '任务类型';
COMMENT ON COLUMN scheduler_task_info.task_name IS '任务名';
COMMENT ON COLUMN scheduler_task_info.task_desc IS '任务描述';
COMMENT ON COLUMN scheduler_task_info.task_status IS '任务状态';
COMMENT ON COLUMN scheduler_task_info.group_name IS '组名';
COMMENT ON COLUMN scheduler_task_info.job_name IS 'job名';
COMMENT ON COLUMN scheduler_task_info.trigger_name IS 'trigger名';
COMMENT ON COLUMN scheduler_task_info.job_class_name IS 'job类名';
COMMENT ON COLUMN scheduler_task_info.cron_expression IS 'cron表达式';
COMMENT ON COLUMN scheduler_task_info.enabled IS '是否启用';
COMMENT ON COLUMN scheduler_task_info.version IS '版本号';

-- 创建触发器
CREATE TRIGGER update_scheduler_task_info_updated_at
    BEFORE UPDATE ON scheduler_task_info
    FOR EACH ROW
    EXECUTE FUNCTION update_updated_at_column();

-- 任务执行记录
CREATE TABLE IF NOT EXISTS scheduler_task_record (
    id BIGSERIAL PRIMARY KEY,
    created_by VARCHAR(64) NOT NULL DEFAULT 'sys',
    updated_by VARCHAR(64) NOT NULL DEFAULT 'sys',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted BOOLEAN DEFAULT FALSE,
    deleted_id BIGINT DEFAULT 0,
    task_id BIGINT DEFAULT 0,
    scheduled_fire_time TIMESTAMP(6),
    fire_time TIMESTAMP(6),
    delay_time BIGINT,
    job_run_time BIGINT,
    previous_fire_time TIMESTAMP(6),
    next_fire_time TIMESTAMP(6),
    vetoed BOOLEAN DEFAULT FALSE,
    is_error BOOLEAN DEFAULT FALSE,
    error_message VARCHAR(1024),
    version INT DEFAULT 0
);

-- 添加注释
COMMENT ON TABLE scheduler_task_record IS '任务执行记录';
COMMENT ON COLUMN scheduler_task_record.id IS '主键ID';
COMMENT ON COLUMN scheduler_task_record.created_by IS '创建人';
COMMENT ON COLUMN scheduler_task_record.updated_by IS '修改人';
COMMENT ON COLUMN scheduler_task_record.created_at IS '创建时间';
COMMENT ON COLUMN scheduler_task_record.updated_at IS '修改时间';
COMMENT ON COLUMN scheduler_task_record.deleted IS '是否删除';
COMMENT ON COLUMN scheduler_task_record.deleted_id IS '删除ID';
COMMENT ON COLUMN scheduler_task_record.task_id IS '任务ID';
COMMENT ON COLUMN scheduler_task_record.scheduled_fire_time IS '调度时间戳';
COMMENT ON COLUMN scheduler_task_record.fire_time IS '执行时间戳';
COMMENT ON COLUMN scheduler_task_record.delay_time IS '延迟时间';
COMMENT ON COLUMN scheduler_task_record.job_run_time IS '执行花费时间';
COMMENT ON COLUMN scheduler_task_record.previous_fire_time IS '上一次执行时间戳';
COMMENT ON COLUMN scheduler_task_record.next_fire_time IS '下一次执行时间戳';
COMMENT ON COLUMN scheduler_task_record.vetoed IS '是否被否决';
COMMENT ON COLUMN scheduler_task_record.is_error IS '是否异常';
COMMENT ON COLUMN scheduler_task_record.error_message IS '错误信息';
COMMENT ON COLUMN scheduler_task_record.version IS '版本号';

-- 创建索引
CREATE INDEX IF NOT EXISTS idx_scheduler_task_record_task_id ON scheduler_task_record(task_id);

-- 创建触发器
CREATE TRIGGER update_scheduler_task_record_updated_at
    BEFORE UPDATE ON scheduler_task_record
    FOR EACH ROW
    EXECUTE FUNCTION update_updated_at_column();

-- 应用注册表
CREATE TABLE IF NOT EXISTS scheduler_app_registry (
    id BIGSERIAL PRIMARY KEY,
    created_by VARCHAR(64) NOT NULL DEFAULT 'sys',
    updated_by VARCHAR(64) NOT NULL DEFAULT 'sys',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted BOOLEAN DEFAULT FALSE,
    deleted_id BIGINT DEFAULT 0,
    app_code VARCHAR(128) NOT NULL,
    app_name VARCHAR(128) NOT NULL,
    app_secret VARCHAR(128) NOT NULL,
    app_desc VARCHAR(1024),
    app_status VARCHAR(16) NOT NULL,
    group_name VARCHAR(128),
    version INT DEFAULT 0,
    UNIQUE (app_code, deleted_id)
);

-- 添加注释
COMMENT ON TABLE scheduler_app_registry IS '应用注册表';
COMMENT ON COLUMN scheduler_app_registry.id IS '主键ID';
COMMENT ON COLUMN scheduler_app_registry.created_by IS '创建人';
COMMENT ON COLUMN scheduler_app_registry.updated_by IS '修改人';
COMMENT ON COLUMN scheduler_app_registry.created_at IS '创建时间';
COMMENT ON COLUMN scheduler_app_registry.updated_at IS '修改时间';
COMMENT ON COLUMN scheduler_app_registry.deleted IS '是否删除';
COMMENT ON COLUMN scheduler_app_registry.deleted_id IS '删除ID';
COMMENT ON COLUMN scheduler_app_registry.app_code IS '应用编号';
COMMENT ON COLUMN scheduler_app_registry.app_name IS '应用名称';
COMMENT ON COLUMN scheduler_app_registry.app_secret IS '应用密钥';
COMMENT ON COLUMN scheduler_app_registry.app_desc IS '应用描述';
COMMENT ON COLUMN scheduler_app_registry.app_status IS '应用状态';
COMMENT ON COLUMN scheduler_app_registry.group_name IS '组名';
COMMENT ON COLUMN scheduler_app_registry.version IS '版本号';

-- 创建更新时间的触发器
CREATE TRIGGER update_scheduler_app_registry_updated_at
    BEFORE UPDATE ON scheduler_app_registry
    FOR EACH ROW
    EXECUTE FUNCTION update_updated_at_column();

-- 应用任务表
CREATE TABLE IF NOT EXISTS scheduler_app_task (
    id BIGSERIAL PRIMARY KEY,
    created_by VARCHAR(64) NOT NULL DEFAULT 'sys',
    updated_by VARCHAR(64) NOT NULL DEFAULT 'sys',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted BOOLEAN DEFAULT FALSE,
    deleted_id BIGINT DEFAULT 0,
    app_code VARCHAR(128) NOT NULL,
    group_name VARCHAR(128),
    task_name VARCHAR(128) NOT NULL,
    task_desc VARCHAR(128),
    task_status VARCHAR(16) NOT NULL,
    params TEXT,
    task_id BIGINT DEFAULT 0,
    version INT DEFAULT 0,
    UNIQUE (app_code, task_name, deleted_id)
);

-- 添加注释
COMMENT ON TABLE scheduler_app_task IS '应用任务表';
COMMENT ON COLUMN scheduler_app_task.id IS '主键ID';
COMMENT ON COLUMN scheduler_app_task.created_by IS '创建人';
COMMENT ON COLUMN scheduler_app_task.updated_by IS '修改人';
COMMENT ON COLUMN scheduler_app_task.created_at IS '创建时间';
COMMENT ON COLUMN scheduler_app_task.updated_at IS '修改时间';
COMMENT ON COLUMN scheduler_app_task.deleted IS '是否删除';
COMMENT ON COLUMN scheduler_app_task.deleted_id IS '删除ID';
COMMENT ON COLUMN scheduler_app_task.app_code IS '所属应用';
COMMENT ON COLUMN scheduler_app_task.group_name IS '组名';
COMMENT ON COLUMN scheduler_app_task.task_name IS '任务名称';
COMMENT ON COLUMN scheduler_app_task.task_desc IS '任务描述';
COMMENT ON COLUMN scheduler_app_task.task_status IS '任务状态';
COMMENT ON COLUMN scheduler_app_task.params IS '任务参数';
COMMENT ON COLUMN scheduler_app_task.task_id IS '任务ID';
COMMENT ON COLUMN scheduler_app_task.version IS '版本号';

-- 创建更新时间的触发器
CREATE TRIGGER update_scheduler_app_task_updated_at
    BEFORE UPDATE ON scheduler_app_task
    FOR EACH ROW
    EXECUTE FUNCTION update_updated_at_column();

