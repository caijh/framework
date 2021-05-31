package com.github.caijh.framework.core.utils

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class PropertyResolverTest {

    @BeforeEach
    fun setUp() {
    }

    @AfterEach
    fun tearDown() {
    }

    @Test
    fun methodToProperty() {
        val lambda = Lambda.resolve(Person::getName)
        val property = PropertyResolver.methodToProperty(lambda.implMethodName)
        Assertions.assertEquals("name", property)
    }

}
