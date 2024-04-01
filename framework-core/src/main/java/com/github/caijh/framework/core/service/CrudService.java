package com.github.caijh.framework.core.service;


import org.springframework.lang.NonNull;

public interface CrudService<T, I> {
    T getOneOrNull(@NonNull I id);

    <S extends T> S save(@NonNull S entity);

    void deleteById(@NonNull I id);
}
