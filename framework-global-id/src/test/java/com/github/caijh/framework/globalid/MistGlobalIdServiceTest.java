package com.github.caijh.framework.globalid;

import com.github.caijh.framework.global.id.service.GlobalIdService;
import com.github.caijh.framework.global.id.service.MistGlobalIdServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

public class MistGlobalIdServiceTest {

    @BeforeEach
    public void setUp() {

    }

    @AfterEach
    public void tearDown() {

    }

    public void generate() {
        GlobalIdService globalIdService = new MistGlobalIdServiceImpl();
        for (long i = 0; i < 1000; i++) {
            long id = globalIdService.generate(i);
            System.out.printf(String.valueOf(id));
        }
    }

}
