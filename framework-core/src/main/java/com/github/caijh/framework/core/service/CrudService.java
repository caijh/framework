package com.github.caijh.framework.core.service;

import jakarta.annotation.Nonnull;

public interface CrudService<T, I> {
    T getOneOrNull(@Nonnull I id);

    <S extends T> S save(@Nonnull S entity);

    void deleteById(@Nonnull I id);
}
