
-- 公共配置表
CREATE TABLE IF NOT EXISTS common_config_info (
    id BIGSERIAL PRIMARY KEY,
    created_by VARCHAR(64) NOT NULL DEFAULT 'sys',
    updated_by VARCHAR(64) NOT NULL DEFAULT 'sys',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted BOOLEAN DEFAULT FALSE,
    deleted_id BIGINT DEFAULT 0,
    username VARCHAR(64) NOT NULL,
    source_code VARCHAR(128) NOT NULL,
    source_id BIGINT NOT NULL,
    remark VARCHAR(128),
    enabled BOOLEAN DEFAULT TRUE,
    version INT DEFAULT 0,
    UNIQUE (source_code, source_id, deleted_id)
);

-- 添加注释
COMMENT ON TABLE common_config_info IS '公共配置表';
COMMENT ON COLUMN common_config_info.id IS '主键ID';
COMMENT ON COLUMN common_config_info.created_by IS '创建人';
COMMENT ON COLUMN common_config_info.updated_by IS '修改人';
COMMENT ON COLUMN common_config_info.created_at IS '创建时间';
COMMENT ON COLUMN common_config_info.updated_at IS '修改时间';
COMMENT ON COLUMN common_config_info.deleted IS '是否删除';
COMMENT ON COLUMN common_config_info.deleted_id IS '删除ID';
COMMENT ON COLUMN common_config_info.username IS '所属用户';
COMMENT ON COLUMN common_config_info.source_code IS '来源数据类型';
COMMENT ON COLUMN common_config_info.source_id IS '来源数据ID';
COMMENT ON COLUMN common_config_info.remark IS '备注';
COMMENT ON COLUMN common_config_info.enabled IS '是否启用';
COMMENT ON COLUMN common_config_info.version IS '版本号';

-- 创建更新时间的触发器
CREATE TRIGGER update_common_config_info_updated_at
    BEFORE UPDATE ON common_config_info
    FOR EACH ROW
    EXECUTE FUNCTION update_updated_at_column();

-- 公共配置项表
CREATE TABLE IF NOT EXISTS common_config_property (
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
    property_value VARCHAR(128),
    property_extend TEXT,
    property_sort INT DEFAULT 0,
    parent_id BIGINT DEFAULT 0,
    enabled BOOLEAN DEFAULT TRUE,
    version INT DEFAULT 0,
    UNIQUE (data_id, property_code, deleted_id)
);

-- 添加注释
COMMENT ON TABLE common_config_property IS '公共配置项表';
COMMENT ON COLUMN common_config_property.id IS '主键ID';
COMMENT ON COLUMN common_config_property.created_by IS '创建人';
COMMENT ON COLUMN common_config_property.updated_by IS '修改人';
COMMENT ON COLUMN common_config_property.created_at IS '创建时间';
COMMENT ON COLUMN common_config_property.updated_at IS '修改时间';
COMMENT ON COLUMN common_config_property.deleted IS '是否删除';
COMMENT ON COLUMN common_config_property.deleted_id IS '删除ID';
COMMENT ON COLUMN common_config_property.data_id IS '配置-ID';
COMMENT ON COLUMN common_config_property.property_code IS '配置项-编码';
COMMENT ON COLUMN common_config_property.property_name IS '配置项-名称';
COMMENT ON COLUMN common_config_property.property_desc IS '配置项-描述';
COMMENT ON COLUMN common_config_property.property_type IS '配置项-类型';
COMMENT ON COLUMN common_config_property.property_value IS '配置项-值';
COMMENT ON COLUMN common_config_property.property_extend IS '配置项-扩展字段';
COMMENT ON COLUMN common_config_property.property_sort IS '配置项-排序号';
COMMENT ON COLUMN common_config_property.parent_id IS '配置项-父属性ID';
COMMENT ON COLUMN common_config_property.enabled IS '是否启用';
COMMENT ON COLUMN common_config_property.version IS '版本号';

-- 创建更新时间的触发器
CREATE TRIGGER update_common_config_property_updated_at
    BEFORE UPDATE ON common_config_property
    FOR EACH ROW
    EXECUTE FUNCTION update_updated_at_column();
