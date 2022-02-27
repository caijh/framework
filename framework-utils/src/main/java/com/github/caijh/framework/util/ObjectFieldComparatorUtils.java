package com.github.caijh.framework.util;

import java.util.List;

public class ObjectFieldComparatorUtils {

    private static final ObjectFieldComparator OBJECT_FIELD_COMPARATOR = new ObjectFieldComparatorImpl();

    private ObjectFieldComparatorUtils() {}

    public static List<ComparableFieldResult> compare(Object o1, Object o2) {
        return OBJECT_FIELD_COMPARATOR.compare(o1, o2);
    }

    public static List<ComparableFieldResult> compare(Object o1, Object o2, String... fields) {
        return OBJECT_FIELD_COMPARATOR.compare(o1, o2, fields);
    }

}
