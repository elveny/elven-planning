package site.elven.planning.boot.base.web.service.property.impl;

import site.elven.planning.boot.base.web.dal.property.BasePropertyRepository;
import site.elven.planning.boot.base.web.model.entity.property.BasePropertyEntity;
import site.elven.planning.boot.base.web.service.base.impl.ServiceImpl;
import site.elven.planning.boot.base.web.service.property.BasePropertyService;

import java.util.List;

public class BasePropertyServiceImpl<E extends BasePropertyEntity, ID,
        R extends BasePropertyRepository<E, ID>>
        extends ServiceImpl<E, ID, R>
        implements BasePropertyService<E, ID, R> {
    @Override
    public List<E> findByDataId(ID dataId) {
        return repository.findByDataId(dataId);
    }
}
