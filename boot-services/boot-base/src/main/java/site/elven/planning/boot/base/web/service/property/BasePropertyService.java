package site.elven.planning.boot.base.web.service.property;

import site.elven.planning.boot.base.web.dal.property.BasePropertyRepository;
import site.elven.planning.boot.base.web.model.entity.property.BasePropertyEntity;
import site.elven.planning.boot.base.web.service.base.IService;

import java.util.List;

public interface BasePropertyService<E extends BasePropertyEntity, ID,
        R extends BasePropertyRepository<E, ID>>
        extends IService<E, ID, R> {
    List<E> findByDataId(ID dataId);
}
