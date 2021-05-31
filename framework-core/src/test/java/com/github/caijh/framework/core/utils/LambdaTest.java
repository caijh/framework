package com.github.caijh.framework.core.utils;


import java.lang.invoke.SerializedLambda;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LambdaTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void resolve() {
        SerializedLambda lambda = Lambda.resolve(Person::getName);
        Assertions.assertEquals("getName", lambda.getImplMethodName());
    }

}
