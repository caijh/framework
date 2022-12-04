package com.github.caijh.framework.data.entity;

import java.io.Serializable;

import jakarta.persistence.MappedSuperclass;

/**
 * 抽象基础实体类, AbstractEntity can not be changed to interface.
 *
 * @param <T> 实体类id类型
 */
@MappedSuperclass
public abstract class AbstractEntity<T extends Serializable> implements PersistentObject<T> {

}
