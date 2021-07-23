package com.github.caijh.framework.data.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.lang.Nullable;

/**
 * 基础Repository
 *
 * @param <T> 实体.
 * @param <I> 实体主键类型
 */
@NoRepositoryBean
public interface BaseRepository<T, I> extends JpaRepository<T, I>, JpaSpecificationExecutor<T> {

    @Nullable
    default T getOneOrNull(I id) {
        return this.findById(id).orElse(null);
    }

}
