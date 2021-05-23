package com.github.caijh.framework.data;

import com.github.caijh.framework.data.jpa.BaseRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseService<T, I> extends BaseRepository<T, I> {
}
