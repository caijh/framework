package com.github.caijh.framework.config.nacos.controller

import org.springframework.beans.factory.annotation.Value
import org.springframework.cloud.context.config.annotation.RefreshScope
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/config")
@RefreshScope
class ConfigController {
    @Value("\${useLocalCache:false}")
    private val useLocalCache = false

    @RequestMapping("/get")
    fun get(): Boolean {
        return useLocalCache
    }
}
