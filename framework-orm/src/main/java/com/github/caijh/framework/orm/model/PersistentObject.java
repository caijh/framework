package com.github.caijh.framework.orm.model;

import java.io.Serializable;

public interface PersistentObject<T extends Serializable> extends Serializable {

    T getId();

}
