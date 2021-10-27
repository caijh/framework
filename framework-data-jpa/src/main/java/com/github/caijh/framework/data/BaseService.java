package com.github.caijh.framework.data;

import com.github.caijh.framework.data.jpa.BaseRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * 基础数据服务类.
 * @param <T> 实体对象类
 * @param <I> 实体对象id类型
 */
@NoRepositoryBean
public interface BaseService<T, I> extends BaseRepository<T, I> {

}
