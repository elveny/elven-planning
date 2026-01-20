package site.elven.planning.boot.base.common.constant;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public interface DatabaseDefaultConstants {
    String CREATED_BY = CommonConstants.COMMON_DEFAULT_SYSTEM_USER;
    String UPDATED_BY = CommonConstants.COMMON_DEFAULT_SYSTEM_USER;
    static LocalDateTime CREATED_AT(){
        return LocalDateTime.now();
    }
    static LocalDateTime UPDATED_AT() {
        return LocalDateTime.now();
    }

    boolean UNDELETED = false;

    int VERSION = 0;

    static Timestamp TIMESTAMP() {
        return new Timestamp(System.currentTimeMillis());
    }
}
