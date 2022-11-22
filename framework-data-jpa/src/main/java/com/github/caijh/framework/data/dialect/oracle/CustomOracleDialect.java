package com.github.caijh.framework.data.dialect.oracle;

import java.sql.Types;

import org.hibernate.dialect.Oracle12cDialect;

public class CustomOracleDialect extends Oracle12cDialect {

    public CustomOracleDialect() {
        super();
        this.registerColumnType(Types.JAVA_OBJECT, "json");
        this.registerColumnType(Types.OTHER, "json");
    }

}
