package com.github.caijh.framework.data.jpa;

import java.util.*;
import javax.annotation.Nonnull;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

/**
 * 基础数据服务类, interfaces in copy from BaseRepository.
 *
 * @param <T> 实体对象类
 * @param <I> 实体对象id类型
 */
public interface BaseService<T, I> {

    List<T> findAll();

    List<T> findAll(@Nonnull Sort sort);

    List<T> findAll(Specification<T> spec);

    Page<T> findAll(Specification<T> spec, @Nonnull Pageable pageable);

    List<T> findAll(Specification<T> spec, @Nonnull Sort sort);

    <S extends T> List<S> findAll(@Nonnull Example<S> example);

    <S extends T> List<S> findAll(@Nonnull Example<S> example, @Nonnull Sort sort);

    Page<T> findAll(@Nonnull Pageable pageable);

    <S extends T> Page<S> findAll(@Nonnull Example<S> example, @Nonnull Pageable pageable);

    List<T> findAllById(@Nonnull Iterable<I> ids);

    <S extends T> List<S> saveAll(@Nonnull Iterable<S> entities);

    void flush();

    <S extends T> S saveAndFlush(@Nonnull S entity);

    void deleteInBatch(@Nonnull Iterable<T> entities);

    void deleteAllInBatch();

    T getOne(@Nonnull I id);

    default T getOneOrNull(I id) {
        return this.findById(id).orElse(null);
    }

    <S extends T> S save(@Nonnull S entity);

    Optional<T> findById(@Nonnull I id);

    boolean existsById(@Nonnull I id);

    long count();

    <S extends T> long count(@Nonnull Example<S> example);

    long count(Specification<T> spec);

    void deleteById(@Nonnull I id);

    void delete(@Nonnull T entity);

    void deleteAll(@Nonnull Iterable<? extends T> entities);

    void deleteAll();

    <S extends T> Optional<S> findOne(@Nonnull Example<S> example);

    Optional<T> findOne(Specification<T> spec);

    <S extends T> boolean exists(@Nonnull Example<S> example);

}
