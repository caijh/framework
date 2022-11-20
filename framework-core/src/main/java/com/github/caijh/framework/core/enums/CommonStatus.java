package com.github.caijh.framework.core.enums;

public enum CommonStatus implements IndexEnum {
    IN_USE(1), DELETED(2);

    private final int index;

    CommonStatus(int index) {
        this.index = index;
    }

    @Override
    public int getIndex() {
        return this.index;
    }
}
