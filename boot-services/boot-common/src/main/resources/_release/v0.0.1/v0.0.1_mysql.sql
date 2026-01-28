
-- 公共配置表
CREATE TABLE IF NOT EXISTS common_config_info (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
	created_by VARCHAR(64) NOT NULL DEFAULT 'sys' COMMENT '创建人',
	updated_by VARCHAR(64) NOT NULL DEFAULT 'sys' COMMENT '修改人',
	created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
	deleted BOOLEAN DEFAULT FALSE COMMENT '是否删除',
	deleted_id BIGINT DEFAULT 0 COMMENT '删除ID',
    username VARCHAR(64) NOT NULL COMMENT '所属用户',
    source_code VARCHAR(128) NOT NULL COMMENT '来源数据类型',
    source_id BIGINT NOT NULL COMMENT '来源数据ID',
    remark VARCHAR(128) COMMENT '备注',
	enabled BOOLEAN DEFAULT TRUE COMMENT '是否启用',
	version INT DEFAULT 0 COMMENT '版本号',
	UNIQUE (source_code, source_id, deleted_id)
);

-- 公共配置项表
CREATE TABLE IF NOT EXISTS common_config_property (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
	created_by VARCHAR(64) NOT NULL DEFAULT 'sys' COMMENT '创建人',
	updated_by VARCHAR(64) NOT NULL DEFAULT 'sys' COMMENT '修改人',
	created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
	deleted BOOLEAN DEFAULT FALSE COMMENT '是否删除',
	deleted_id BIGINT DEFAULT 0 COMMENT '删除ID',
    data_id BIGINT NOT NULL COMMENT '配置-ID',
    property_code VARCHAR(128) NOT NULL COMMENT '配置项-编码',
    property_name VARCHAR(128) NOT NULL COMMENT '配置项-名称',
    property_desc VARCHAR(256) COMMENT '配置项-描述',
    property_type VARCHAR(128) NOT NULL COMMENT '配置项-类型',
    property_value VARCHAR(128) COMMENT '配置项-值',
    property_extend LONGTEXT COMMENT '配置项-扩展字段',
    property_sort INT DEFAULT 0 COMMENT '配置项-排序号',
    parent_id BIGINT DEFAULT 0 COMMENT '配置项-父属性ID',
	enabled BOOLEAN DEFAULT TRUE COMMENT '是否启用',
	version INT DEFAULT 0 COMMENT '版本号',
    UNIQUE (config_id, property_code, deleted_id)
);

