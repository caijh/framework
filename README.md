# framework

这不是真的框架，只是在SpringBoot基础上封装默认的配置，统一orm框架、缓存、日志的使用库，并整合一些通用的代码。

## framework-core

包含可以被其他模块所引用的类，定义共用的常量类、异常类等能被其他framework模块使用的类。

## framework-config-xxxx

framework-config-xxxx 包含了xxx对应的通用配置项。

## framework-data-xx

### framework-data-mybatis

1. PaginationInnerInterceptor分页拦截
2. 自动填充，实现并声明InsertFillFieldProvider和UpdateFillFieldProvider两个Bean

## framework-web

1. CorsAutoConfiguration 配置一个CorsFIlter Bean ，并添加映射路径和具体的CORS配置路径。
2. ThreadLocalStoreInterceptor处理请求后清理threadlocal变量

## framework-utils

1. XmlUtils
2. YamlUtils
3. EasyExcel
