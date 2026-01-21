-- 用户表
CREATE TABLE IF NOT EXISTS user_info (
    id BIGSERIAL PRIMARY KEY,
    created_by VARCHAR(64) NOT NULL DEFAULT 'sys',
    updated_by VARCHAR(64) NOT NULL DEFAULT 'sys',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted BOOLEAN DEFAULT FALSE,
    deleted_id BIGINT DEFAULT 0,
    code VARCHAR(64) NOT NULL,
    username VARCHAR(64) NOT NULL,
    password VARCHAR(128) NOT NULL,
    email VARCHAR(128),
    phone_number VARCHAR(16),
    first_name VARCHAR(64),
    last_name VARCHAR(64),
    date_of_birth DATE,
    gender CHAR(1),
    type VARCHAR(16) DEFAULT 'user',
    status VARCHAR(16) DEFAULT 'active',
    last_active_time TIMESTAMP,
    last_login_time TIMESTAMP,
    last_logout_time TIMESTAMP,
    locked BOOLEAN DEFAULT FALSE,
    expire_time TIMESTAMP(6),
    enabled BOOLEAN DEFAULT TRUE,
    version INT DEFAULT 0,
    UNIQUE (code, deleted_id),
    UNIQUE (username, deleted_id),
    UNIQUE (email, deleted_id),
    UNIQUE (phone_number, deleted_id)
    );

-- 添加约束：类型和状态的检查约束
ALTER TABLE user_info ADD CONSTRAINT chk_user_type CHECK (type IN ('admin', 'user'));
ALTER TABLE user_info ADD CONSTRAINT chk_user_status CHECK (status IN ('active', 'inactive'));
ALTER TABLE user_info ADD CONSTRAINT chk_user_gender CHECK (gender IN ('M', 'F', 'O'));

-- 添加注释
COMMENT ON TABLE user_info IS '用户表';
COMMENT ON COLUMN user_info.id IS '主键ID';
COMMENT ON COLUMN user_info.created_by IS '创建人';
COMMENT ON COLUMN user_info.updated_by IS '修改人';
COMMENT ON COLUMN user_info.created_at IS '创建时间';
COMMENT ON COLUMN user_info.updated_at IS '修改时间';
COMMENT ON COLUMN user_info.deleted IS '是否删除';
COMMENT ON COLUMN user_info.deleted_id IS '删除ID';
COMMENT ON COLUMN user_info.code IS '用户编码';
COMMENT ON COLUMN user_info.username IS '用户名';
COMMENT ON COLUMN user_info.password IS '密码';
COMMENT ON COLUMN user_info.email IS '电子邮箱';
COMMENT ON COLUMN user_info.phone_number IS '电话号码';
COMMENT ON COLUMN user_info.first_name IS '名字';
COMMENT ON COLUMN user_info.last_name IS '姓氏';
COMMENT ON COLUMN user_info.date_of_birth IS '出生日期';
COMMENT ON COLUMN user_info.gender IS '性别（M: 男, F: 女, O: 其他）';
COMMENT ON COLUMN user_info.type IS '用户类型（admin: 管理员, user: 普通用户）';
COMMENT ON COLUMN user_info.status IS '用户状态（active: 激活, inactive: 未激活）';
COMMENT ON COLUMN user_info.last_active_time IS '最后活跃时间';
COMMENT ON COLUMN user_info.last_login_time IS '最后登录时间';
COMMENT ON COLUMN user_info.last_logout_time IS '最后登出时间';
COMMENT ON COLUMN user_info.locked IS '是否锁定';
COMMENT ON COLUMN user_info.expire_time IS '过期时间';
COMMENT ON COLUMN user_info.enabled IS '是否启用';
COMMENT ON COLUMN user_info.version IS '版本号';

-- 创建更新时间的触发器
CREATE TRIGGER update_user_info_updated_at
    BEFORE UPDATE ON user_info
    FOR EACH ROW
    EXECUTE FUNCTION update_updated_at_column();
