package com.github.caijh.framework.data;

import java.util.List;
import java.util.Optional;
import javax.inject.Inject;

import com.github.caijh.framework.data.jpa.BaseRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

/**
 * 基础数据服务实现类.
 * @param <T> 实体类型
 * @param <I> 实体id类型
 */
public abstract class BaseServiceImpl<T, I> implements BaseService<T, I> {

    @Inject
    protected BaseRepository<T, I> repository;

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

    @NotNull
    @Override
    public List<T> findAll() {
        return this.repository.findAll();
    }

    @NotNull
    @Override
    public List<T> findAll(@NotNull Sort sort) {
        return this.repository.findAll(sort);
    }

    @NotNull
    @Override
    public List<T> findAll(Specification<T> spec) {
        return this.repository.findAll(spec);
    }

    @NotNull
    @Override
    public Page<T> findAll(Specification<T> spec, @NotNull Pageable pageable) {
        return this.repository.findAll(spec, pageable);
    }

    @NotNull
    @Override
    public List<T> findAll(Specification<T> spec, @NotNull Sort sort) {
        return this.repository.findAll(spec, sort);
    }

    @NotNull
    @Override
    public <S extends T> List<S> findAll(@NotNull Example<S> example) {
        return this.repository.findAll(example);
    }

    @NotNull
    @Override
    public <S extends T> List<S> findAll(@NotNull Example<S> example, @NotNull Sort sort) {
        return this.repository.findAll(example, sort);
    }

    @NotNull
    @Override
    public Page<T> findAll(@NotNull Pageable pageable) {
        return this.repository.findAll(pageable);
    }

    @NotNull
    @Override
    public <S extends T> Page<S> findAll(@NotNull Example<S> example, @NotNull Pageable pageable) {
        return this.repository.findAll(example, pageable);
    }

    @NotNull
    @Override
    public List<T> findAllById(@NotNull Iterable<I> ids) {
        return this.repository.findAllById(ids);
    }

    @NotNull
    @Override
    public <S extends T> List<S> saveAll(@NotNull Iterable<S> entities) {
        return this.repository.saveAll(entities);
    }

    @Override
    public void flush() {
        this.repository.flush();
    }

    @NotNull
    @Override
    public <S extends T> S saveAndFlush(@NotNull S entity) {
        return this.repository.saveAndFlush(entity);
    }

    @Override
    public void deleteInBatch(@NotNull Iterable<T> entities) {
        this.repository.deleteInBatch(entities);
    }

    @Override
    public void deleteAllInBatch() {
        this.repository.deleteAllInBatch();
    }

    @NotNull
    @Override
    public T getOne(@NotNull I id) {
        return this.repository.getOne(id);
    }

    @NotNull
    @Override
    public <S extends T> S save(@NotNull S entity) {
        return this.repository.save(entity);
    }

    @NotNull
    @Override
    public Optional<T> findById(@NotNull I id) {
        return this.repository.findById(id);
    }

    @Override
    public boolean existsById(@NotNull I id) {
        return this.repository.existsById(id);
    }

    @Override
    public long count() {
        return this.repository.count();
    }

    @Override
    public <S extends T> long count(@NotNull Example<S> example) {
        return this.repository.count(example);
    }

    @Override
    public long count(Specification<T> spec) {
        return this.repository.count(spec);
    }

    @Override
    public void deleteById(@NotNull I id) {
        this.repository.deleteById(id);
    }

    @Override
    public void delete(@NotNull T entity) {
        this.repository.delete(entity);
    }

    @Override
    public void deleteAll(@NotNull Iterable<? extends T> entities) {
        this.repository.deleteAll(entities);
    }

    @Override
    public void deleteAll() {
        this.repository.deleteAll();
    }

    @NotNull
    @Override
    public <S extends T> Optional<S> findOne(@NotNull Example<S> example) {
        return this.repository.findOne(example);
    }

    @NotNull
    @Override
    public Optional<T> findOne(Specification<T> spec) {
        return this.repository.findOne(spec);
    }

    @Override
    public <S extends T> boolean exists(@NotNull Example<S> example) {
        return this.repository.exists(example);
    }

}
