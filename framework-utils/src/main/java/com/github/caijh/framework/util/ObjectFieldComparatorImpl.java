package com.github.caijh.framework.util;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.github.caijh.commons.util.ReflectUtils;
import com.google.common.collect.Lists;
import org.jetbrains.annotations.NotNull;

public class ObjectFieldComparatorImpl implements ObjectFieldComparator {

    @Override
    public List<ComparableFieldResult> compare(Object o1, Object o2) {
        this.assertNotSame(o1, o2);

        List<ComparableFieldResult> results = Lists.newArrayList();
        Arrays.stream(o1.getClass().getDeclaredFields())
              .filter(e -> e.getAnnotation(ComparableField.class) != null)
              .collect(Collectors.toList())
              .forEach(field -> results.add(this.getComparableFieldResult(field, o1, o2)));

        return results;
    }

    @Override
    public List<ComparableFieldResult> compare(Object o1, Object o2, String... fields) {
        this.assertNotSame(o1, o2);

        List<String> fieldNames = Arrays.stream(fields).collect(Collectors.toList());
        List<ComparableFieldResult> results = Lists.newArrayList();
        Arrays.stream(o1.getClass().getDeclaredFields())
              .filter(e -> fieldNames.contains(e.getName()))
              .collect(Collectors.toList())
              .forEach(field -> results.add(this.getComparableFieldResult(field, o1, o2)));

        return results;
    }

    @Override
    public List<ComparableFieldResult> compareAllFields(Object o1, Object o2, String... ignoreFields) {
        this.assertNotSame(o1, o2);
        List<String> ignoreFieldNames = Arrays.stream(ignoreFields).collect(Collectors.toList());
        List<ComparableFieldResult> results = Lists.newArrayList();
        Arrays.stream(o1.getClass().getDeclaredFields())
              .filter(e -> !ignoreFieldNames.contains(e.getName()))
              .collect(Collectors.toList())
              .forEach(field -> results.add(this.getComparableFieldResult(field, o1, o2)));
        return results;
    }

    private void assertNotSame(Object o1, Object o2) {
        if (o1 == o2) {
            throw new IllegalArgumentException("o1 and o2 must not be the same object");
        }
    }

    @NotNull
    private ComparableFieldResult getComparableFieldResult(Field field, Object o1, Object o2) {
        ComparableFieldResult result = new ComparableFieldResult();
        String fieldName = field.getName();
        result.setFieldName(fieldName);
        try {
            Object o1FieldValue = ReflectUtils.invokeGetter(o1, fieldName);
            Object o2FieldValue = ReflectUtils.invokeGetter(o2, fieldName);
            result.setFieldEqual(o1FieldValue.equals(o2FieldValue));
        } catch (Exception e) {
            result.setFieldEqual(false);
        }
        return result;
    }

}
