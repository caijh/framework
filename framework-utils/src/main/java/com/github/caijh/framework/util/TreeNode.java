package com.github.caijh.framework.util;

import java.util.List;

/**
 * represent tree node.
 *
 * @param <K> key type
 * @param <T> value type
 */
public interface TreeNode<K, T> {

    K getId();

    K getParentId();

    List<T> getChildren();

}
