package com.github.caijh.framework.data;

import java.util.List;
import java.util.Optional;

import org.jetbrains.annotations.NotNull;
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

    List<T> findAll(@NotNull Sort sort);

    List<T> findAll(Specification<T> spec);

    Page<T> findAll(Specification<T> spec, @NotNull Pageable pageable);

    List<T> findAll(Specification<T> spec, @NotNull Sort sort);

    <S extends T> List<S> findAll(@NotNull Example<S> example);

    <S extends T> List<S> findAll(@NotNull Example<S> example, @NotNull Sort sort);

    Page<T> findAll(@NotNull Pageable pageable);

    <S extends T> Page<S> findAll(@NotNull Example<S> example, @NotNull Pageable pageable);

    List<T> findAllById(@NotNull Iterable<I> ids);

    <S extends T> List<S> saveAll(@NotNull Iterable<S> entities);

    void flush();

    <S extends T> S saveAndFlush(@NotNull S entity);

    void deleteInBatch(@NotNull Iterable<T> entities);

    void deleteAllInBatch();

    T getOne(@NotNull I id);

    <S extends T> S save(@NotNull S entity);

    Optional<T> findById(@NotNull I id);

    boolean existsById(@NotNull I id);

    long count();

    <S extends T> long count(@NotNull Example<S> example);

    long count(Specification<T> spec);

    void deleteById(@NotNull I id);

    void delete(@NotNull T entity);

    void deleteAll(@NotNull Iterable<? extends T> entities);

    void deleteAll();

    <S extends T> Optional<S> findOne(@NotNull Example<S> example);

    Optional<T> findOne(Specification<T> spec);

    <S extends T> boolean exists(@NotNull Example<S> example);

}
