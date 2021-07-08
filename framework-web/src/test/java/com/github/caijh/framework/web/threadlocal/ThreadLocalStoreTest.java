package com.github.caijh.framework.web.threadlocal;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ThreadLocalStoreTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void putAndGetThread() throws InterruptedException {
        Thread thread = new Thread(() -> ThreadLocalStore.put("thread_test", 1));
        thread.start();
        thread.join();
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            Integer i = ThreadLocalStore.get("thread_test");
            System.out.println(i);
        });
        ThreadLocalStore.put("thread_test", 1);
        Integer thread_test = ThreadLocalStore.get("thread_test");
        Assertions.assertEquals(1, thread_test);
    }

}
