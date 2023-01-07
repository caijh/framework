package com.github.caijh.framework.data.jpa;

import java.util.Optional;

import com.github.caijh.framework.core.threadlocal.ThreadLocalStore;
import org.springframework.data.domain.AuditorAware;

public class CustomizeAuditorAware implements AuditorAware<Long> {

    @Override
    public Optional<Long> getCurrentAuditor() {
        Long userId = ThreadLocalStore.get("X-User-Id");
        return Optional.ofNullable(userId);
    }

}
