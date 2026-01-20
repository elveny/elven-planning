package site.elven.planning.boot.base.web.mapstruct.base;

import site.elven.planning.boot.base.web.model.entity.base.BaseEntity;
import site.elven.planning.api.web.model.base.request.base.BaseReq;
import site.elven.planning.api.web.model.base.response.base.BaseRsp;

import java.util.List;
import java.util.stream.Collectors;

public interface BaseMapStruct<E extends BaseEntity, REQ extends BaseReq, RSP extends BaseRsp> {
    E req2Entity(REQ req);
    RSP entity2Rsp(E entity);

    default List<E> req2Entity(List<REQ> reqList){
        if(reqList == null){
            return null;
        }
        return reqList.stream().map(this::req2Entity).collect(Collectors.toList());
    }
    default List<RSP> entity2Rsp(List<E> entityList){
        if(entityList == null){
            return null;
        }
        return entityList.stream().map(this::entity2Rsp).collect(Collectors.toList());
    }
}
