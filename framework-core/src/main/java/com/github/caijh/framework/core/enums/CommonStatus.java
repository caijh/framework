package com.github.caijh.framework.core.enums;

/**
 * 共用状态.
 */
public enum CommonStatus implements IndexEnum {
    IN_USE(0),
    DELETED(1);

    private final Integer index;

    CommonStatus(int index) {
        this.index = index;
    }

    @Override
    public int getIndex() {
        return this.index;
    }
}
