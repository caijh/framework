package com.github.caijh.framework.core.service;

import jakarta.annotation.Nonnull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PagingService<T> {

    Page<T> findAll(@Nonnull Pageable pageable);
}
