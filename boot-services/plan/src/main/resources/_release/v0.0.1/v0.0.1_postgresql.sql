-- 创建更新时间的触发器函数
CREATE OR REPLACE FUNCTION update_updated_at_column()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ language 'plpgsql';

-- 计划模板信息
CREATE TABLE IF NOT EXISTS plan_template_info (
    id BIGSERIAL PRIMARY KEY,
    created_by VARCHAR(64) NOT NULL DEFAULT 'sys',
    updated_by VARCHAR(64) NOT NULL DEFAULT 'sys',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted BOOLEAN DEFAULT FALSE,
    deleted_id BIGINT DEFAULT 0,
    username VARCHAR(64) NOT NULL,
    template_code VARCHAR(128) NOT NULL,
    template_name VARCHAR(128) NOT NULL,
    template_intro VARCHAR(1024) DEFAULT NULL,
    template_type VARCHAR(32) DEFAULT NULL,
    is_default BOOLEAN DEFAULT FALSE,
    enabled BOOLEAN DEFAULT TRUE,
    version INT DEFAULT 0,
    UNIQUE (username, template_name, deleted_id)
);

-- 添加注释
COMMENT ON TABLE plan_template_info IS '计划模板信息表';
COMMENT ON COLUMN plan_template_info.created_by IS '创建人';
COMMENT ON COLUMN plan_template_info.updated_by IS '修改人';
COMMENT ON COLUMN plan_template_info.created_at IS '创建时间';
COMMENT ON COLUMN plan_template_info.updated_at IS '修改时间';
COMMENT ON COLUMN plan_template_info.deleted IS '是否删除';
COMMENT ON COLUMN plan_template_info.deleted_id IS '删除ID';
COMMENT ON COLUMN plan_template_info.username IS '所属用户';
COMMENT ON COLUMN plan_template_info.template_code IS '模板编码';
COMMENT ON COLUMN plan_template_info.template_name IS '模板名称';
COMMENT ON COLUMN plan_template_info.template_intro IS '模板简介';
COMMENT ON COLUMN plan_template_info.template_type IS '模板类型';
COMMENT ON COLUMN plan_template_info.is_default IS '是否默认';
COMMENT ON COLUMN plan_template_info.enabled IS '是否启用';
COMMENT ON COLUMN plan_template_info.version IS '版本号';

-- 创建触发器
CREATE TRIGGER update_plan_template_info_updated_at
    BEFORE UPDATE ON plan_template_info
    FOR EACH ROW
    EXECUTE FUNCTION update_updated_at_column();


 -- 计划模板属性
 CREATE TABLE IF NOT EXISTS plan_template_property (
     id BIGSERIAL PRIMARY KEY,
     created_by VARCHAR(64) NOT NULL DEFAULT 'sys',
     updated_by VARCHAR(64) NOT NULL DEFAULT 'sys',
     created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
     updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
     deleted BOOLEAN DEFAULT FALSE,
     deleted_id BIGINT DEFAULT 0,
     data_id BIGINT NOT NULL,
     property_code VARCHAR(128) NOT NULL,
     property_name VARCHAR(128) NOT NULL,
     property_desc VARCHAR(256),
     property_type VARCHAR(128) NOT NULL,
     property_value VARCHAR(1024),
     property_extend TEXT,
     property_sort INT DEFAULT 0,
     parent_id BIGINT DEFAULT 0,
     enabled BOOLEAN DEFAULT TRUE,
     version INT DEFAULT 0,
     UNIQUE (data_id, property_code, deleted_id)
 );

 -- 添加注释
 COMMENT ON TABLE plan_template_property IS '计划模板属性表';
 COMMENT ON COLUMN plan_template_property.created_by IS '创建人';
 COMMENT ON COLUMN plan_template_property.updated_by IS '修改人';
 COMMENT ON COLUMN plan_template_property.created_at IS '创建时间';
 COMMENT ON COLUMN plan_template_property.updated_at IS '修改时间';
 COMMENT ON COLUMN plan_template_property.deleted IS '是否删除';
 COMMENT ON COLUMN plan_template_property.deleted_id IS '删除ID';
 COMMENT ON COLUMN plan_template_property.data_id IS '关联数据ID';
 COMMENT ON COLUMN plan_template_property.property_code IS '属性编码';
 COMMENT ON COLUMN plan_template_property.property_name IS '属性名称';
 COMMENT ON COLUMN plan_template_property.property_desc IS '属性描述';
 COMMENT ON COLUMN plan_template_property.property_type IS '属性类型';
 COMMENT ON COLUMN plan_template_property.property_value IS '属性值';
 COMMENT ON COLUMN plan_template_property.property_extend IS '属性扩展字段';
 COMMENT ON COLUMN plan_template_property.property_sort IS '属性排序号';
 COMMENT ON COLUMN plan_template_property.parent_id IS '属性父属性ID';
 COMMENT ON COLUMN plan_template_property.enabled IS '是否启用';
 COMMENT ON COLUMN plan_template_property.version IS '版本号';

 -- 创建触发器
 CREATE TRIGGER update_plan_template_property_updated_at
     BEFORE UPDATE ON plan_template_property
     FOR EACH ROW
     EXECUTE FUNCTION update_updated_at_column();

-- 计划任务信息
CREATE TABLE IF NOT EXISTS plan_task_info (
    id BIGSERIAL PRIMARY KEY,
    created_by VARCHAR(64) NOT NULL DEFAULT 'sys',
    updated_by VARCHAR(64) NOT NULL DEFAULT 'sys',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted BOOLEAN DEFAULT FALSE,
    deleted_id BIGINT DEFAULT 0,
    username VARCHAR(64) NOT NULL,
    title VARCHAR(128) NOT NULL,
    description VARCHAR(1024) DEFAULT NULL,
    mode VARCHAR(32) DEFAULT NULL,
    priority INT DEFAULT 0,
    status VARCHAR(32) DEFAULT NULL,
    is_public BOOLEAN DEFAULT FALSE,
    template_id BIGINT DEFAULT 0,
    parent_id BIGINT DEFAULT 0,
    version INT DEFAULT 0
);

-- 创建索引
CREATE INDEX IF NOT EXISTS idx_plan_task_info_username ON plan_task_info(username);
CREATE INDEX IF NOT EXISTS idx_plan_task_info_title ON plan_task_info(title);

-- 添加注释
COMMENT ON TABLE plan_task_info IS '任务信息表';
COMMENT ON COLUMN plan_task_info.created_by IS '创建人';
COMMENT ON COLUMN plan_task_info.updated_by IS '修改人';
COMMENT ON COLUMN plan_task_info.created_at IS '创建时间';
COMMENT ON COLUMN plan_task_info.updated_at IS '修改时间';
COMMENT ON COLUMN plan_task_info.deleted IS '是否删除';
COMMENT ON COLUMN plan_task_info.deleted_id IS '删除ID';
COMMENT ON COLUMN plan_task_info.username IS '所属用户';
COMMENT ON COLUMN plan_task_info.title IS '任务标题';
COMMENT ON COLUMN plan_task_info.description IS '任务描述';
COMMENT ON COLUMN plan_task_info.mode IS '任务模式';
COMMENT ON COLUMN plan_task_info.priority IS '优先级';
COMMENT ON COLUMN plan_task_info.status IS '任务状态';
COMMENT ON COLUMN plan_task_info.is_public IS '是否公开';
COMMENT ON COLUMN plan_task_info.template_id IS '模板ID';
COMMENT ON COLUMN plan_task_info.parent_id IS '父任务ID';
COMMENT ON COLUMN plan_task_info.version IS '版本号';


-- 创建触发器
CREATE TRIGGER update_plan_task_info_updated_at
    BEFORE UPDATE ON plan_task_info
    FOR EACH ROW
    EXECUTE FUNCTION update_updated_at_column();



 -- 计划任务属性
CREATE TABLE IF NOT EXISTS plan_task_property (
    id BIGSERIAL PRIMARY KEY,
    created_by VARCHAR(64) NOT NULL DEFAULT 'sys',
    updated_by VARCHAR(64) NOT NULL DEFAULT 'sys',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted BOOLEAN DEFAULT FALSE,
    deleted_id BIGINT DEFAULT 0,
    data_id BIGINT NOT NULL,
    property_code VARCHAR(128) NOT NULL,
    property_name VARCHAR(128) NOT NULL,
    property_desc VARCHAR(256),
    property_type VARCHAR(128) NOT NULL,
    property_value VARCHAR(1024),
    property_extend TEXT,
    property_sort INT DEFAULT 0,
    parent_id BIGINT DEFAULT 0,
    enabled BOOLEAN DEFAULT TRUE,
    version INT DEFAULT 0,
    UNIQUE (data_id, property_code, deleted_id)
);

 -- 添加注释
 COMMENT ON TABLE plan_task_property IS '计划模板属性表';
 COMMENT ON COLUMN plan_task_property.created_by IS '创建人';
 COMMENT ON COLUMN plan_task_property.updated_by IS '修改人';
 COMMENT ON COLUMN plan_task_property.created_at IS '创建时间';
 COMMENT ON COLUMN plan_task_property.updated_at IS '修改时间';
 COMMENT ON COLUMN plan_task_property.deleted IS '是否删除';
 COMMENT ON COLUMN plan_task_property.deleted_id IS '删除ID';
 COMMENT ON COLUMN plan_task_property.data_id IS '关联数据ID';
 COMMENT ON COLUMN plan_task_property.property_code IS '属性编码';
 COMMENT ON COLUMN plan_task_property.property_name IS '属性名称';
 COMMENT ON COLUMN plan_task_property.property_desc IS '属性描述';
 COMMENT ON COLUMN plan_task_property.property_type IS '属性类型';
 COMMENT ON COLUMN plan_task_property.property_value IS '属性值';
 COMMENT ON COLUMN plan_task_property.property_extend IS '属性扩展字段';
 COMMENT ON COLUMN plan_task_property.property_sort IS '属性排序号';
 COMMENT ON COLUMN plan_task_property.parent_id IS '属性父属性ID';
 COMMENT ON COLUMN plan_task_property.enabled IS '是否启用';
 COMMENT ON COLUMN plan_task_property.version IS '版本号';

 -- 创建触发器
 CREATE TRIGGER update_plan_task_property_updated_at
     BEFORE UPDATE ON plan_task_property
     FOR EACH ROW
     EXECUTE FUNCTION update_updated_at_column();
