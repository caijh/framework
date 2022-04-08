package com.github.caijh.framework.data.jpa;

import java.util.Locale;

/**
 * table join types.
 */
public enum JoinType {
    LEFT,
    RIGHT,
    INNER,
    FULL;

    public String sqlString() {
        return this.name().toLowerCase(Locale.ROOT) + " join";
    }
}
