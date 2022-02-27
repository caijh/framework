package com.github.caijh.framework.util;

import java.util.List;

/**
 * 对象比较器
 */
public interface ObjectComparator {

    List<ComparableFieldResult> compare(Object o1, Object o2);

}
