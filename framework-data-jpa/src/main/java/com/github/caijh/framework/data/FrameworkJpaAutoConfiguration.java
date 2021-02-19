package com.github.caijh.framework.data;

import com.github.caijh.framework.data.jpa.BaseRepository;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(repositoryBaseClass = BaseRepository.class)
public class FrameworkJpaAutoConfiguration {
}
