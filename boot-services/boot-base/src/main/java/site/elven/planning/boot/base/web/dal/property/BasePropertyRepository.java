package site.elven.planning.boot.base.web.dal.property;

import org.springframework.data.repository.NoRepositoryBean;
import site.elven.planning.boot.base.web.dal.base.BaseRepository;
import site.elven.planning.boot.base.web.model.entity.property.BasePropertyEntity;

import java.util.List;

@NoRepositoryBean
public interface BasePropertyRepository<T extends BasePropertyEntity, ID> extends BaseRepository<T, ID> {
    List<T> findByDataId(ID dataId);
}
