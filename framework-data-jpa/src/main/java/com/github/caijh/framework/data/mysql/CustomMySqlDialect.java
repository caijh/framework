package com.github.caijh.framework.data.mysql;

import java.sql.Types;

import org.hibernate.dialect.MySQL8Dialect;

public class CustomMySqlDialect extends MySQL8Dialect {

    public CustomMySqlDialect() {
        this.registerColumnType(Types.JAVA_OBJECT, "json");
        this.registerColumnType(Types.OTHER, "json");
    }

}
