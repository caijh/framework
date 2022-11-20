package com.github.caijh.framework.data.entity;

import java.io.Serializable;

/**
 * PO持久化对象.
 */
public interface PersistentObject<T extends Serializable> extends Serializable {

    T getId();

}
