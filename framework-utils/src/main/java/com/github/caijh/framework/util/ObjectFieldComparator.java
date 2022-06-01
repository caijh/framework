package com.github.caijh.framework.util;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

import com.github.caijh.commons.util.Asserts;
import com.github.caijh.commons.util.reflection.invoker.GetFieldInvoker;
import com.google.common.collect.Lists;

public interface ObjectFieldComparator {

    default List<ComparableFieldResult> compare(Object o1, Object o2, String... fields) {
        Asserts.isTrue(o1 != o2, () -> new IllegalArgumentException("o1 and o2 must not be same"));

        List<String> fieldNames = Arrays.stream(fields).toList();
        List<ComparableFieldResult> results = Lists.newArrayList();
        Arrays.stream(o1.getClass().getDeclaredFields())
              .filter(e -> fieldNames.contains(e.getName())).toList()
              .forEach(field -> results.add(this.getFieldCompareResult(field, o1, o2)));

        return results;
    }

    default List<ComparableFieldResult> compareAllFields(Object o1, Object o2, String... ignoreFields) {
        Asserts.isTrue(o1 != o2, () -> new IllegalArgumentException("o1 and o2 must not be same"));

        List<String> ignoreFieldNames = Arrays.stream(ignoreFields).toList();
        List<ComparableFieldResult> results = Lists.newArrayList();
        Arrays.stream(o1.getClass().getDeclaredFields())
              .filter(field -> !ignoreFieldNames.contains(field.getName())).toList()
              .forEach(field -> results.add(this.getFieldCompareResult(field, o1, o2)));
        return results;
    }

    default List<ComparableFieldResult> compareComparableField(Object o1, Object o2) {
        Asserts.isTrue(o1 != o2, () -> new IllegalArgumentException("o1 and o2 must not be same"));

        List<ComparableFieldResult> results = Lists.newArrayList();
        Arrays.stream(o1.getClass().getDeclaredFields())
              .filter(e -> e.getAnnotation(ComparableField.class) != null).toList()
              .forEach(field -> results.add(this.getFieldCompareResult(field, o1, o2)));

        return results;
    }

    default ComparableFieldResult getFieldCompareResult(Field field, Object o1, Object o2) {
        ComparableFieldResult result = new ComparableFieldResult();
        String fieldName = field.getName();
        result.setFieldName(fieldName);
        try {
            Object o1FieldValue = new GetFieldInvoker(field).invoke(o1);
            Field o2Field = o2.getClass().getDeclaredField(fieldName);
            Object o2FieldValue = new GetFieldInvoker(o2Field).invoke(o2);
            result.setFieldEqual(o1FieldValue.equals(o2FieldValue));
        } catch (Exception e) {
            result.setFieldEqual(false);
        }
        return result;
    }

}
