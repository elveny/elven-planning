package site.elven.planning.boot.base.web.dal.base;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;
import site.elven.planning.boot.base.web.model.entity.base.BaseEntity;

import java.util.Optional;

@NoRepositoryBean
public interface BaseRepository<T extends BaseEntity, ID> extends JpaRepository<T, ID>, JpaSpecificationExecutor<T> {
    @Modifying
    @Query("UPDATE #{#entityName} e SET e.deleted = true, e.deletedId = e.id WHERE e.id = ?1")
    int logicDeleteById(ID id);

    @Modifying
    @Query("UPDATE #{#entityName} e SET e.deleted = true, e.deletedId = e.id WHERE e = ?1")
    int logicDelete(T entity);

    @Modifying
    @Query("UPDATE #{#entityName} e SET e.deleted = true, e.deletedId = e.id WHERE e.id IN ?1")
    int logicDeleteAllById(Iterable<? extends ID> ids);

    @Modifying
    @Query("UPDATE #{#entityName} e SET e.deleted = true, e.deletedId = e.id WHERE e IN ?1")
    int logicDeleteAllInBatch(Iterable<T> entities);

    /**
     * 根据 ID 查询未删除的实体
     */
    @Query("SELECT e FROM #{#entityName} e WHERE e.id = :id AND e.deleted = false")
    Optional<T> findByIdAndNotDeleted(@Param("id") ID id);

}
