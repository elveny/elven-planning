-- 计划模板信息
CREATE TABLE IF NOT EXISTS plan_template_info (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
	created_by VARCHAR(64) NOT NULL DEFAULT 'sys' COMMENT '创建人',
	updated_by VARCHAR(64) NOT NULL DEFAULT 'sys' COMMENT '修改人',
	created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
	deleted BOOLEAN DEFAULT FALSE COMMENT '是否删除',
	deleted_id BIGINT DEFAULT 0 COMMENT '删除ID',
	username VARCHAR(64) NOT NULL COMMENT '所属用户',
    template_code VARCHAR(128) NOT NULL COMMENT '模板编码',
    template_name VARCHAR(128) NOT NULL COMMENT '模板名称',
    template_intro VARCHAR(1024) DEFAULT NULL COMMENT '模板简介',
    template_type VARCHAR(32) DEFAULT NULL COMMENT '模板类型',
    is_default BOOLEAN DEFAULT FALSE COMMENT '是否默认',
	enabled BOOLEAN DEFAULT TRUE COMMENT '是否启用',
	version INT DEFAULT 0 COMMENT '版本号',
	UNIQUE (username, template_name, deleted_id)
);

-- 计划模板属性
CREATE TABLE IF NOT EXISTS plan_template_property (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
	created_by VARCHAR(64) NOT NULL DEFAULT 'sys' COMMENT '创建人',
	updated_by VARCHAR(64) NOT NULL DEFAULT 'sys' COMMENT '修改人',
	created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
	deleted BOOLEAN DEFAULT FALSE COMMENT '是否删除',
	deleted_id BIGINT DEFAULT 0 COMMENT '删除ID',
    data_id BIGINT NOT NULL COMMENT '关联数据ID',
    property_code VARCHAR(128) NOT NULL COMMENT '属性编码',
    property_name VARCHAR(128) NOT NULL COMMENT '属性名称',
    property_desc VARCHAR(256) COMMENT '属性描述',
    property_type VARCHAR(128) NOT NULL COMMENT '属性类型',
    property_value VARCHAR(1024) COMMENT '属性值',
    property_extend LONGTEXT COMMENT '属性扩展字段',
    property_sort INT DEFAULT 0 COMMENT '属性排序号',
    parent_id BIGINT DEFAULT 0 COMMENT '属性父属性ID',
	enabled BOOLEAN DEFAULT TRUE COMMENT '是否启用',
	version INT DEFAULT 0 COMMENT '版本号',
    UNIQUE (data_id, property_code, deleted_id)
);

-- 计划任务信息
CREATE TABLE IF NOT EXISTS plan_task_info (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
	created_by VARCHAR(64) NOT NULL DEFAULT 'sys' COMMENT '创建人',
	updated_by VARCHAR(64) NOT NULL DEFAULT 'sys' COMMENT '修改人',
	created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
	deleted BOOLEAN DEFAULT FALSE COMMENT '是否删除',
	deleted_id BIGINT DEFAULT 0 COMMENT '删除ID',
	username VARCHAR(64) NOT NULL COMMENT '所属用户',
    title VARCHAR(128) NOT NULL COMMENT '任务标题',
    description VARCHAR(1024) DEFAULT NULL COMMENT '任务描述',
    mode VARCHAR(32) DEFAULT NULL COMMENT '任务模式',
    priority INT DEFAULT 0 COMMENT '优先级',
    status VARCHAR(32) DEFAULT NULL COMMENT '任务状态',
    is_public BOOLEAN DEFAULT FALSE COMMENT '是否公开',
    template_id BIGINT DEFAULT 0 COMMENT '模板ID',
    parent_id BIGINT DEFAULT 0 COMMENT '父任务ID',
	version INT DEFAULT 0 COMMENT '版本号',
	INDEX idx_username(username),
	INDEX idx_title(title)
);

-- 计划任务属性
CREATE TABLE IF NOT EXISTS plan_task_property (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
	created_by VARCHAR(64) NOT NULL DEFAULT 'sys' COMMENT '创建人',
	updated_by VARCHAR(64) NOT NULL DEFAULT 'sys' COMMENT '修改人',
	created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
	deleted BOOLEAN DEFAULT FALSE COMMENT '是否删除',
	deleted_id BIGINT DEFAULT 0 COMMENT '删除ID',
    data_id BIGINT NOT NULL COMMENT '关联数据ID',
    property_code VARCHAR(128) NOT NULL COMMENT '属性编码',
    property_name VARCHAR(128) NOT NULL COMMENT '属性名称',
    property_desc VARCHAR(256) COMMENT '属性描述',
    property_type VARCHAR(128) NOT NULL COMMENT '属性类型',
    property_value VARCHAR(1024) COMMENT '属性值',
    property_extend LONGTEXT COMMENT '属性扩展字段',
    property_sort INT DEFAULT 0 COMMENT '属性排序号',
    parent_id BIGINT DEFAULT 0 COMMENT '属性父属性ID',
	enabled BOOLEAN DEFAULT TRUE COMMENT '是否启用',
	version INT DEFAULT 0 COMMENT '版本号',
    UNIQUE (data_id, property_code, deleted_id)
);