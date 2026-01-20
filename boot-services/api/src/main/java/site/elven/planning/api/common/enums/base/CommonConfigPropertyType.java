package site.elven.planning.api.common.enums.base;

import site.elven.planning.common.util.date.DateUtils;

import java.time.LocalDate;

public enum CommonConfigPropertyType implements Processor {
    STRING {
        @Override
        public boolean validate(String value, String extend) {
            value = getValue(value, extend);
            if (value == null || value.isEmpty()) {
                return false;
            }
            return super.validate(value, extend);
        }

        @Override
        public Object convert(String value, String extend) {
            return super.convert(value, extend);
        }
    },
    NUMBER {
        @Override
        public boolean validate(String value, String extend) {
            value = getValue(value, extend);
            if (value == null || value.isEmpty()) {
                return false;
            }
            try {
                Long.valueOf(value);
                return true;
            }
            catch (NumberFormatException e) {
                try {
                    Double.valueOf(value);
                    return true;
                }
                catch (NumberFormatException e1) {
                    return false;
                }
            }
        }

        @Override
        public Object convert(String value, String extend) {
            value = getValue(value, extend);
            if (value == null || value.isEmpty()) {
                return 0;
            }
            try {
                return Long.valueOf(value);
            }
            catch (NumberFormatException e) {
                try {
                    return Double.valueOf(value);
                }
                catch (NumberFormatException e1) {
                    return 0;
                }
            }
        }

        @Override
        public String format(Object value) {
            return super.format(value);
        }
    },
    BOOLEAN {
        private Boolean result = null;
        @Override
        public boolean validate(String value, String extend) {
            value = getValue(value, extend);
            try {
                if(value != null){
                    result = Boolean.valueOf(value);
                }
                return true;
            }
            catch (Exception e) {
                return false;
            }
        }

        @Override
        public Boolean convert(String value, String extend) {
            if (result != null) {
                return result;
            }

            value = getValue(value, extend);
            if(value != null){
                result = Boolean.valueOf(value);
                return result;
            }

            return null;
        }
    },
    ARRAY,
    JSON {
        @Override
        public boolean validate(String value, String extend) {
            return super.validate(value, extend);
        }

        @Override
        public Object convert(String value, String extend) {
            return super.convert(value, extend);
        }

        @Override
        public String format(Object value) {
            return super.format(value);
        }
    },
    DATE {
        @Override
        public boolean validate(String value, String extend) {
            value = getValue(value, extend);
            if (value == null || value.isEmpty()) {
                return false;
            }
            return DateUtils.isValidDate(value);
        }
        @Override
        public Object convert(String value, String extend) {
            value = getValue(value, extend);
            if (value == null || value.isEmpty()) {
                return null;
            }
            return DateUtils.parseDate(value);
        }
        @Override
        public String format(Object value) {
            return DateUtils.formatDate((LocalDate) value);
        }
    },
    DATETIME,
    TIMESTAMP,
    ENUM,
    SELECT
    ;

    public String getValue(String value, String extend){
        if(value == null){
            value = extend;
        }
        return value;
    }

    @Override
    public boolean validate(String value, String extend) {
        return true;
    }
    @Override
    public Object convert(String value, String extend) {
        return null;
    }
    @Override
    public String format(Object value) {
        if (value != null){
            return value.toString();
        }
        return null;
    }
}

interface Processor {
    // 验证值是否合法
    boolean validate(String value, String extend);

    // 转换值为目标类型
    Object convert(String value, String extend);

    // 格式化值为字符串（用于存储）
    String format(Object value);
}