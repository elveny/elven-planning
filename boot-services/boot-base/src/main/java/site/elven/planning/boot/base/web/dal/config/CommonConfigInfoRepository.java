package site.elven.planning.boot.base.web.dal.config;

import org.springframework.stereotype.Repository;
import site.elven.planning.api.common.enums.base.CommonConfigSourceCode;
import site.elven.planning.boot.base.web.dal.base.BaseRepository;
import site.elven.planning.boot.base.web.model.entity.config.CommonConfigInfo;

@Repository
public interface CommonConfigInfoRepository extends BaseRepository<CommonConfigInfo, Long> {
    CommonConfigInfo findBySourceCodeAndSourceId(CommonConfigSourceCode sourceCode, Long sourceId);
}
