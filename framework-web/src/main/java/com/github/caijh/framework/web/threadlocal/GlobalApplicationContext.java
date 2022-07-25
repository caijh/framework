package com.github.caijh.framework.web.threadlocal;

import java.util.Date;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;

import com.github.caijh.framework.web.util.RequestUtils;
import org.springframework.lang.Nullable;

public class GlobalApplicationContext {

    public static final String X_USER_ID = "X-User-Id";
    private static final String X_REQUEST_IP = "X-Request-Ip";

    private GlobalApplicationContext() {}

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

    public static String getIp() {
        return ThreadLocalStore.get(X_REQUEST_IP);
    }

    public static void setIp(HttpServletRequest request) {
        ThreadLocalStore.put(X_REQUEST_IP, RequestUtils.getIp(request));
    }

    public static void setControllerEnterTime() {
        ThreadLocalStore.put("X-Controller-Enter-Time", new Date());
    }

    public static Date getControllerEnterTime() {
        return ThreadLocalStore.get("X-Controller-Enter-Time");
    }

}
