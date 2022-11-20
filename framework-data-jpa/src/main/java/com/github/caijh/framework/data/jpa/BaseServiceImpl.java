package com.github.caijh.framework.data.jpa;

import java.util.*;
import javax.annotation.Nonnull;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

/**
 * 基础数据服务实现类.
 *
 * @param <T> 实体类型
 * @param <I> 实体id类型
 */
public abstract class BaseServiceImpl<T, I> implements BaseService<T, I> {

    @Inject
    protected BaseRepository<T, I> repository;

    protected Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 将注入BaseRepository转化实际继承了BaseRepository的类.
     *
     * @param <R> repository this extends BaseRepository
     * @return R target repository
     */
    @SuppressWarnings("unchecked")
    public <R extends BaseRepository<T, I>> R getRepository() {
        return (R) this.repository;
    }

    @Nonnull
    @Override
    public List<T> findAll() {
        return this.repository.findAll();
    }

    @Nonnull
    @Override
    public List<T> findAll(@Nonnull Sort sort) {
        return this.repository.findAll(sort);
    }

    @Nonnull
    @Override
    public List<T> findAll(Specification<T> spec) {
        return this.repository.findAll(spec);
    }

    @Nonnull
    @Override
    public Page<T> findAll(Specification<T> spec, @Nonnull Pageable pageable) {
        return this.repository.findAll(spec, pageable);
    }

    @Nonnull
    @Override
    public List<T> findAll(Specification<T> spec, @Nonnull Sort sort) {
        return this.repository.findAll(spec, sort);
    }

    @Nonnull
    @Override
    public <S extends T> List<S> findAll(@Nonnull Example<S> example) {
        return this.repository.findAll(example);
    }

    @Nonnull
    @Override
    public <S extends T> List<S> findAll(@Nonnull Example<S> example, @Nonnull Sort sort) {
        return this.repository.findAll(example, sort);
    }

    @Nonnull
    @Override
    public Page<T> findAll(@Nonnull Pageable pageable) {
        return this.repository.findAll(pageable);
    }

    @Nonnull
    @Override
    public <S extends T> Page<S> findAll(@Nonnull Example<S> example, @Nonnull Pageable pageable) {
        return this.repository.findAll(example, pageable);
    }

    @Nonnull
    @Override
    public List<T> findAllById(@Nonnull Iterable<I> ids) {
        return this.repository.findAllById(ids);
    }

    @Nonnull
    @Override
    public <S extends T> List<S> saveAll(@Nonnull Iterable<S> entities) {
        return this.repository.saveAll(entities);
    }

    @Override
    public void flush() {
        this.repository.flush();
    }

    @Nonnull
    @Override
    public <S extends T> S saveAndFlush(@Nonnull S entity) {
        return this.repository.saveAndFlush(entity);
    }

    @Override
    public void deleteInBatch(@Nonnull Iterable<T> entities) {
        this.repository.deleteAllInBatch(entities);
    }

    @Override
    public void deleteAllInBatch() {
        this.repository.deleteAllInBatch();
    }

    @Nonnull
    @Override
    public T getOne(@Nonnull I id) {
        return this.repository.getReferenceById(id);
    }

    @Nonnull
    @Override
    public <S extends T> S save(@Nonnull S entity) {
        return this.repository.save(entity);
    }

    @Nonnull
    @Override
    public Optional<T> findById(@Nonnull I id) {
        return this.repository.findById(id);
    }

    @Override
    public boolean existsById(@Nonnull I id) {
        return this.repository.existsById(id);
    }

    @Override
    public long count() {
        return this.repository.count();
    }

    @Override
    public <S extends T> long count(@Nonnull Example<S> example) {
        return this.repository.count(example);
    }

    @Override
    public long count(Specification<T> spec) {
        return this.repository.count(spec);
    }

    @Override
    public void deleteById(@Nonnull I id) {
        this.repository.deleteById(id);
    }

    @Override
    public void delete(@Nonnull T entity) {
        this.repository.delete(entity);
    }

    @Override
    public void deleteAll(@Nonnull Iterable<? extends T> entities) {
        this.repository.deleteAll(entities);
    }

    @Override
    public void deleteAll() {
        this.repository.deleteAll();
    }

    @Nonnull
    @Override
    public <S extends T> Optional<S> findOne(@Nonnull Example<S> example) {
        return this.repository.findOne(example);
    }

    @Nonnull
    @Override
    public Optional<T> findOne(Specification<T> spec) {
        return this.repository.findOne(spec);
    }

    @Override
    public <S extends T> boolean exists(@Nonnull Example<S> example) {
        return this.repository.exists(example);
    }

}
