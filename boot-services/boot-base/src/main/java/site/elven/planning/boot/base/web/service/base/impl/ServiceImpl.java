package site.elven.planning.boot.base.web.service.base.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;
import site.elven.planning.boot.base.manager.message.MessageManager;
import site.elven.planning.boot.base.web.dal.base.BaseRepository;
import site.elven.planning.boot.base.web.model.entity.base.BaseEntity;
import site.elven.planning.api.web.model.base.response.page.PageRsp;
import site.elven.planning.boot.base.web.service.base.IService;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Transactional
public class ServiceImpl<E extends BaseEntity, ID,
        R extends BaseRepository<E, ID>>
        implements IService<E, ID, R> {

    @Autowired
    protected R repository;

    @PersistenceContext
    protected EntityManager entityManager;

    @Autowired
    protected MessageManager messageManager;



    protected void enableDeleteFilter() {
        Session session = entityManager.unwrap(Session.class);
        session.enableFilter("delete_filter").setParameter("deleted", false);
    }

    public R getRepository() {
        return repository;
    }

    @Override
    public PageRsp<E> list(E search, int pageNumber, int pageSize, Sort sort, String[] ignorePaths,
                           Map<String, ExampleMatcher.MatcherConfigurer<ExampleMatcher.GenericPropertyMatcher>> matchers) {
        // 判空
        if (search == null){
            return new PageRsp<>(0, 0, 0, Collections.emptyList());
        }

        // 默认只查询未“逻辑删除”的数据
        if (search.getDeleted() == null){
            search.setDeleted(false);
        }

        // 配置 ExampleMatcher
        ExampleMatcher matcher = ExampleMatcher.matching()
                // 忽略大小写
                .withIgnoreCase()
                .withIgnorePaths("id", "createdBy", "updatedBy", "createdAt", "updatedAt", "version")
                // withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)：使用部分匹配（即模糊匹配）。
                // DEFAULT：默认匹配（通常为精确匹配）。
                // STARTING：前缀匹配。
                // ENDING：后缀匹配。
                // CONTAINING：部分匹配（模糊匹配）。
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
                ;

        // 忽略查询条件字段
        if (ignorePaths != null){
            matcher.withIgnorePaths(ignorePaths);
        }

        // 自定义 matchers：字段自定义特殊匹配规则
        if(matchers != null){
            for (Map.Entry<String, ExampleMatcher.MatcherConfigurer<ExampleMatcher.GenericPropertyMatcher>> entry : matchers.entrySet()) {
                matcher.withMatcher(entry.getKey(), entry.getValue());
            }
        }

        // 创建 Example 对象
        Example<E> example = Example.of(search, matcher);

        // 创建分页对象
        Pageable pageable = pageable(pageNumber, pageSize, sort);

        // 查询分页数据
        Page<E> page = findAll(example, pageable);
        return buildPageRsp(page);
    }

    @Override
    public PageRsp<E> list(Specification<E> spec, int pageNumber, int pageSize, Sort sort){
        // 拼接 deleted 条件
        Specification<E> deletedSpec = (root, query, cb) -> cb.equal(root.get("deleted"), false);
        spec = spec.and(deletedSpec);
        // 创建分页对象
        Pageable pageable = pageable(pageNumber, pageSize, sort);
        // 查询分页数据
        Page<E> page = findAll(spec, pageable);
        return buildPageRsp(page);
    }

    protected PageRsp<E> buildPageRsp(Page<E> page) {
        int totalPages = page.getTotalPages();
        long totalElements = page.getTotalElements();
        int numberOfElements = page.getNumberOfElements();
        List<E> content = page.getContent();

        // 返回分页数据
        return new PageRsp<>(totalPages, totalElements, numberOfElements, content);
    }

    /**
     * 创建分页对象
     *   pageNumber需要自减1；
     *   默认排序：id 降序
     * @param pageNumber 页码
     * @param pageSize 每页大小
     * @param sort 排序
     * @return Pageable
     */
    protected static Pageable pageable(int pageNumber, int pageSize, Sort sort) {
        if (pageNumber < 1) {
            pageNumber = 1;
        }
        if (pageSize < 1) {
            pageSize = 10;
        }
        pageNumber = pageNumber - 1;
        return PageRequest.of(pageNumber, pageSize, Objects.requireNonNullElseGet(sort, () -> Sort.by(Sort.Order.desc("id"))));
    }

}


