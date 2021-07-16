package com.github.caijh.framework.core.util;

import com.github.caijh.framework.core.lambda.SerializedLambda;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LambdaUtilsTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void resolve() {
        SerializedLambda lambda = LambdaUtils.resolve(Person::getName);
        Assertions.assertEquals("getName", lambda.getImplMethodName());

        String field = PropertyResolver.methodToProperty(lambda.getImplMethodName());
        Assertions.assertEquals("name", field);

        field = PropertyResolver.methodToProperty(Person::getName);
        Assertions.assertEquals("name", field);
    }

}
