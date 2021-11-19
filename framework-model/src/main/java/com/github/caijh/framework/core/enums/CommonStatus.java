package com.github.caijh.framework.core.enums;

/**
 * 通用状态.
 */
public enum CommonStatus implements IndexEnum {
    /**
     * 在用
     */
    IN_USE(0),
    /**
     * 逻辑删除
     */
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
