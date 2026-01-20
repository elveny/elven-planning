package site.elven.planning.boot.base.web.service.base;

import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.query.FluentQuery;
import site.elven.planning.boot.base.common.constant.DatabaseDefaultConstants;
import site.elven.planning.boot.base.web.dal.base.BaseRepository;
import site.elven.planning.boot.base.web.model.entity.base.BaseEntity;
import site.elven.planning.api.web.model.base.response.page.PageRsp;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

public interface IService<E extends BaseEntity, ID,
        R extends BaseRepository<E, ID>> {
    /**
     * 获取 Repository 对象
     * @return Repository 对象
     */
    R getRepository();

    /**
     * 保存一个实体
     * @param e 实体对象
     * @return 保存后的实体对象
     */
    default E save(E e) {
        if(e.getCreatedBy() == null || "".equals(e.getCreatedBy())){
            e.setCreatedBy(DatabaseDefaultConstants.CREATED_BY);
        }
        if(e.getUpdatedBy() == null || "".equals(e.getUpdatedBy())){
            e.setUpdatedBy(DatabaseDefaultConstants.UPDATED_BY);
        }
        if(e.getCreatedAt() == null){
            e.setCreatedAt(DatabaseDefaultConstants.CREATED_AT());
        }
        if (e.getUpdatedAt() == null){
            e.setUpdatedAt(DatabaseDefaultConstants.UPDATED_AT());
        }
        if(e.getDeleted() == null){
            e.setDeleted(DatabaseDefaultConstants.UNDELETED);
        }
        if(e.getDeletedId() == null){
            e.setDeletedId(0L);
        }
        if (e.getVersion() == null){
            e.setVersion(DatabaseDefaultConstants.VERSION);
        }
        return getRepository().save(e);
    }

    /**
     * 更新一个实体
     * @param e 实体对象
     * @return 更新后的实体对象
     */
    default E update(E e) {
        return getRepository().save(e);
    }

    /**
     * 根据ID删除一个实体
     * @param id 实体ID
     */
    default void deleteById(ID id) {
        getRepository().deleteById(id);
    }

    /**
     * 根据实体删除一个实体
     * @param entity 实体对象
     */
    default void delete(E entity){
        getRepository().delete(entity);
    }

    /**
     * 根据ID查找一个实体
     * @param id 实体ID
     * @return 实体对象
     */
    default E findById(ID id) {
        return getRepository().findById(id).orElse(null);
    }

    default E findOne(E search){
        return findOne(Example.of(search));
    }

    /**
     * 条件查询：查询单个满足条件的记录
     * @param example 条件对象
     * @return 查询结果
     * @param <T> 实体类型
     */
    default <T extends E> T findOne(Example<T> example) {
        return getRepository().findOne(example).orElse(null);
    }

    /**
     * 判断是否存在某个ID的实体
     * @param id 实体ID
     * @return 是否存在
     */
    default boolean existsById(ID id){
        return getRepository().existsById(id);
    }

    /**
     * 条件查询：判断是否存在满足条件的记录
     * @param example 条件对象
     * @return 是否存在
     * @param <T> 实体类型
     */
    default <T extends E> boolean exists(Example<T> example) {
        return getRepository().exists(example);
    }

    /**
     * 统计实体总数
     * @return 实体总数
     */
    default long count(){
        return getRepository().count();
    }

    /**
     * 条件查询：统计满足条件的记录总数
     * @param example 条件对象
     * @return 记录总数
     * @param <T> 实体类型
     */
    default <T extends E> long count(Example<T> example) {
        return getRepository().count(example);
    }

    /**
     * 批量保存实体
     * @param entities 实体集合
     * @return 实体集合
     */
    default <T extends E> List<T> saveAll(Iterable<T> entities){
        return getRepository().saveAll(entities);
    }

    /**
     * 获取所有实体
     * @return 实体集合
     */
    default List<E> findAll(){
        return getRepository().findAll();
    }

    /**
     * 获取所有实体，按排序规则排序
     * @param sort 排序规则
     * @return 实体集合
     */
    default List<E> findAll(Sort sort) {
        return getRepository().findAll(sort);
    }

    /**
     * 批量获取实体
     * @param ids 实体ID集合
     * @return 实体集合
     */
    default List<E> findAllById(Iterable<ID> ids){
        return getRepository().findAllById(ids);
    }

    /**
     * 条件查询：查询所有满足条件的记录
     * @param example 条件对象
     * @return 查询结果
     * @param <T> 实体类型
     */
    default <T extends E> List<T> findAll(Example<T> example) {
        return getRepository().findAll(example);
    }

    /**
     * 批量查询：查询所有满足条件的记录
     * @param search 搜索条件
     * @return 批量查询结果
     */
    default List<E> findAll(E search) {
        return findAll(Example.of(search));
    }

    /**
     * 条件查询：按排序规则查询所有满足条件的记录
     * @param example 条件对象
     * @param sort 排序规则
     * @return 查询结果
     * @param <T> 实体类型
     */
    default <T extends E> List<T> findAll(Example<T> example, Sort sort) {
        return getRepository().findAll(example, sort);
    }

    /**
     * 批量查询：按排序规则查询所有满足条件的记录
     * @param search 查询条件
     * @param sort 排序规则
     * @return 批量查询结果
     */
    default List<E> findAll(E search, Sort sort) {
        return findAll(Example.of(search), sort);
    }

    /**
     * 条件查询：按分页规则查询所有满足条件的记录
     * @param example 条件对象
     * @param pageable 分页规则
     * @return 查询结果
     * @param <T> 实体类型
     */
    default <T extends E> Page<T> findAll(Example<T> example, Pageable pageable) {
        return getRepository().findAll(example, pageable);
    }

    /**
     * 分页查询：按分页规则查询所有记录
     * @param pageable 分页规则
     * @return 查询结果
     */
    default Page<E> findAll(Pageable pageable) {
        return getRepository().findAll(pageable);
    }

    /**
     * 批量删除：删除指定实体集合的记录
     * @param entities 实体集合
     */
    default void deleteAllInBatch(Iterable<E> entities){
        getRepository().deleteAllInBatch(entities);
    }

    /**
     * 批量删除：删除指定ID集合的记录
     * @param ids ID集合
     */
    default void deleteAllByIdInBatch(Iterable<ID> ids){
        getRepository().deleteAllByIdInBatch(ids);
    }

    /**
     * 批量删除：删除所有记录
     */
    default void deleteAllInBatch(){
        getRepository().deleteAllInBatch();
    }

    /**
     * 逐条删除：删除指定ID集合的记录
     * @param ids ID集合
     */
    default void deleteAllById(Iterable<? extends ID> ids){
        getRepository().deleteAllById(ids);
    }

    /**
     * 逐条删除：删除指定实体集合的记录
     * @param entities 实体集合
     */
    default void deleteAll(Iterable<? extends E> entities){
        getRepository().deleteAll(entities);
    }

    /**
     * 逐条删除：删除所有记录
     */
    default void deleteAll(){
        getRepository().deleteAll();
    }

    /**
     * 条件查询：按指定函数查询满足条件的记录
     * @param example 条件对象
     * @param queryFunction 查询函数
     * @return 查询结果
     * @param <T> 实体类型
     * @param <S> 查询结果类型
     */
    default <T extends E, S> S findBy(Example<T> example, Function<FluentQuery.FetchableFluentQuery<T>, S> queryFunction) {
        return getRepository().findBy(example, queryFunction);
    }

    /**
     * 条件查询：查询单个满足条件的记录
     * @param spec 条件对象
     * @return 查询结果
     */
    default E findOne(Specification<E> spec) {
        return getRepository().findOne(spec).orElse(null);
    }

    /**
     * 条件查询：查询所有满足条件的记录
     * @param spec 条件对象
     * @return 查询结果
     */
    default List<E> findAll(Specification<E> spec) {
        return getRepository().findAll(spec);
    }

    /**
     * 条件查询：按分页规则查询所有满足条件的记录
     * @param spec 条件对象
     * @param pageable 分页规则
     * @return 查询结果
     */
    default Page<E> findAll(Specification<E> spec, Pageable pageable) {
        return getRepository().findAll(spec, pageable);
    }

    /**
     * 条件查询：按排序规则查询所有满足条件的记录
     * @param spec 条件对象
     * @param sort 排序规则
     * @return 查询结果
     */
    default List<E> findAll(Specification<E> spec, Sort sort) {
        return getRepository().findAll(spec, sort);
    }

    /**
     * 条件查询：统计满足条件的记录总数
     * @param spec 条件对象
     * @return 记录总数
     */
    default long count(Specification<E> spec) {
        return getRepository().count(spec);
    }

    /**
     * 条件查询：判断是否存在满足条件的记录
     * @param spec 条件对象
     * @return 是否存在满足条件的记录
     */
    default boolean exists(Specification<E> spec) {
        return getRepository().exists(spec);
    }

    /**
     * 条件删除：删除满足条件的记录
     * @param spec 条件对象
     * @return 删除的记录总数
     */
    default long delete(Specification<E> spec) {
        return getRepository().delete(spec);
    }

    /**
     * 条件查询：按指定函数查询满足条件的记录
     * @param spec 条件对象
     * @param queryFunction 查询函数
     * @return 查询结果
     * @param <T> 实体类型
     * @param <S> 查询结果类型
     */
    default <T extends E, S> S findBy(Specification<E> spec, Function<FluentQuery.FetchableFluentQuery<T>, S> queryFunction) {
        return getRepository().findBy(spec, queryFunction);
    }

    /**
     * 条件查询：按分页规则查询所有满足条件的记录
     * @param search 查询条件
     * @param pageNumber 页码
     * @param pageSize 页大小
     * @param sort 排序规则
     * @param ignorePaths 忽略路径
     * @param matchers 匹配器配置
     * @return 查询结果
     */
    PageRsp<E> list(E search, int pageNumber, int pageSize, Sort sort, String[] ignorePaths, Map<String, ExampleMatcher.MatcherConfigurer<ExampleMatcher.GenericPropertyMatcher>> matchers);

    /**
     * 条件查询：按分页规则查询所有满足条件的记录
     * @param spec 条件对象
     * @param pageNumber 页码
     * @param pageSize 页大小
     * @param sort 排序规则
     * @return 查询结果
     */
    PageRsp<E> list(Specification<E> spec, int pageNumber, int pageSize, Sort sort);

    /**
     * 根据ID逻辑删除一个实体
     * @param id 实体ID
     */
    default int logicDeleteById(ID id) {
        return getRepository().logicDeleteById(id);
    }

    /**
     * 根据实体逻辑删除一个实体
     * @param entity 实体对象
     */
    default int logicDelete(E entity) {
        return getRepository().logicDelete(entity);
    }

    /**
     * 根据ID批量逻辑删除实体
     * @param ids 实体ID集合
     */
    default int logicDeleteAllById(Iterable<? extends ID> ids){
        return getRepository().logicDeleteAllById(ids);
    }

    /**
     * 根据实体批量逻辑删除实体
     * @param entities 实体集合
     */
    default int logicDeleteAllInBatch(Iterable<E> entities){
        return getRepository().logicDeleteAllInBatch(entities);
    }
}
