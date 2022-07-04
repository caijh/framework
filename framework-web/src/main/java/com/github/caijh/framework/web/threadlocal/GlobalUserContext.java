package com.github.caijh.framework.web.threadlocal;

import java.util.Optional;

import org.springframework.lang.Nullable;

public class GlobalUserContext {

    public static final String X_USER_ID = "X-User-Id";

    private GlobalUserContext() {}

    public static Long getCurrentUserId() {
        Long userId = ThreadLocalStore.get(X_USER_ID);
        return Optional.ofNullable(userId).orElseThrow(IllegalStateException::new);
    }

    public static void setCurrentUserId(Long userId) {
        ThreadLocalStore.put(X_USER_ID, userId);
    }

    @Nullable
    public static Long getCurrentUserIdOrNull() {
        return ThreadLocalStore.get(X_USER_ID);
    }

}
