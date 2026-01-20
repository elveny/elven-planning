-- 定时任务表
CREATE TABLE IF NOT EXISTS scheduler_task_info (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
	created_by VARCHAR(64) NOT NULL DEFAULT 'sys' COMMENT '创建人',
	updated_by VARCHAR(64) NOT NULL DEFAULT 'sys' COMMENT '修改人',
	created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
	deleted BOOLEAN DEFAULT FALSE COMMENT '是否删除',
	deleted_id BIGINT DEFAULT 0 COMMENT '删除ID',
    username VARCHAR(64) NOT NULL COMMENT '所属用户',
    task_type VARCHAR(16) NOT NULL COMMENT '任务类型',
    task_name VARCHAR(128) NOT NULL COMMENT '任务名',
    task_desc VARCHAR(128) NOT NULL COMMENT '任务描述',
    task_status VARCHAR(16) NOT NULL COMMENT '任务状态',
    group_name VARCHAR(128) NOT NULL COMMENT '组名',
    job_name VARCHAR(128) NOT NULL COMMENT 'job名',
    trigger_name VARCHAR(128) NOT NULL COMMENT 'trigger名',
    job_class_name VARCHAR(128) NOT NULL COMMENT 'job类名',
    cron_expression VARCHAR(128) NOT NULL COMMENT 'cron表达式',
	enabled BOOLEAN DEFAULT TRUE COMMENT '是否启用',
	version INT DEFAULT 0 COMMENT '版本号',
	UNIQUE (task_name, deleted_id),
	UNIQUE (group_name, job_name, deleted_id)
);

-- 任务执行记录
SET sql_mode = (SELECT REPLACE(@@sql_mode, 'NO_ZERO_DATE', ''));
CREATE TABLE IF NOT EXISTS scheduler_task_record (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
	created_by VARCHAR(64) NOT NULL DEFAULT 'sys' COMMENT '创建人',
	updated_by VARCHAR(64) NOT NULL DEFAULT 'sys' COMMENT '修改人',
	created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
	deleted BOOLEAN DEFAULT FALSE COMMENT '是否删除',
	deleted_id BIGINT DEFAULT 0 COMMENT '删除ID',
    task_id BIGINT DEFAULT 0 COMMENT '任务ID',
    scheduled_fire_time TIMESTAMP(6) COMMENT '调度时间戳',
    fire_time TIMESTAMP(6) COMMENT '执行时间戳',
    delay_time BIGINT COMMENT '延迟时间',
    job_run_time BIGINT COMMENT '执行花费时间',
    previous_fire_time TIMESTAMP(6) COMMENT '上一次执行时间戳',
    next_fire_time TIMESTAMP(6) COMMENT '下一次执行时间戳',
    vetoed BOOLEAN DEFAULT FALSE COMMENT '是否被否决',
    is_error BOOLEAN DEFAULT FALSE COMMENT '是否异常',
    error_message VARCHAR(1024) COMMENT '错误信息',
	version INT DEFAULT 0 COMMENT '版本号',
	INDEX idx_task_id (task_id)
);

-- 应用注册表
CREATE TABLE IF NOT EXISTS scheduler_app_registry (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
	created_by VARCHAR(64) NOT NULL DEFAULT 'sys' COMMENT '创建人',
	updated_by VARCHAR(64) NOT NULL DEFAULT 'sys' COMMENT '修改人',
	created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
	deleted BOOLEAN DEFAULT FALSE COMMENT '是否删除',
	deleted_id BIGINT DEFAULT 0 COMMENT '删除ID',
    app_code VARCHAR(128) NOT NULL COMMENT '应用编号',
    app_name VARCHAR(128) NOT NULL COMMENT '应用名称',
    app_secret VARCHAR(128) NOT NULL COMMENT '应用密钥',
    app_desc VARCHAR(128) NOT NULL COMMENT '应用描述',
    app_status VARCHAR(16) NOT NULL COMMENT '应用状态',
    group_name VARCHAR(128) DEFAULT NULL COMMENT '组名',
	version INT DEFAULT 0 COMMENT '版本号',
	UNIQUE (app_code, deleted_id)
);

-- 应用任务表
CREATE TABLE IF NOT EXISTS scheduler_app_task (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
	created_by VARCHAR(64) NOT NULL DEFAULT 'sys' COMMENT '创建人',
	updated_by VARCHAR(64) NOT NULL DEFAULT 'sys' COMMENT '修改人',
	created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
	deleted BOOLEAN DEFAULT FALSE COMMENT '是否删除',
	deleted_id BIGINT DEFAULT 0 COMMENT '删除ID',
    app_code VARCHAR(128) NOT NULL COMMENT '所属应用',
    group_name VARCHAR(128) DEFAULT NULL COMMENT '组名',
    task_name VARCHAR(128) NOT NULL COMMENT '任务名称',
    task_desc VARCHAR(128) NOT NULL COMMENT '任务描述',
    task_status VARCHAR(16) NOT NULL COMMENT '任务状态',
    params LONGTEXT COMMENT '任务参数',
    task_id BIGINT DEFAULT 0 COMMENT '任务ID',
	version INT DEFAULT 0 COMMENT '版本号',
	UNIQUE (app_code, task_name, deleted_id)
);
