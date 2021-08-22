package com.github.caijh.framework.config.nacos

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class FrameworkConfigNacosTestApplication

fun main(args: Array<String>) {
    runApplication<FrameworkConfigNacosTestApplication>(*args)
}
