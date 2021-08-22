package com.github.caijh.framework.test.microservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class FrameworkMicroserviceTestApplication

fun main(args: Array<String>) {
    runApplication<FrameworkMicroserviceTestApplication>(*args)
}
