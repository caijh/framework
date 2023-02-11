package com.github.caijh.framework.data.entity;

import java.io.Serializable;

import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class LogicDelEntity<T extends Serializable> extends AuditingEntity<T> {

    private Boolean deleted;

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

}
