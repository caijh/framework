package com.github.caijh.framework.util;

public class ObjectFieldComparatorUtils {

    private static final ObjectFieldComparator OBJECT_FIELD_COMPARATOR = new ObjectFieldComparator() {};

    private ObjectFieldComparatorUtils() {}

    public static ObjectFieldComparator getObjectFieldComparator() {
        return OBJECT_FIELD_COMPARATOR;
    }

}
