package com.github.caijh.framework.util;

import java.util.List;

public interface ObjectFieldComparator extends ObjectComparator {

    List<ComparableFieldResult> compare(Object o1, Object o2, String... fields);

    List<ComparableFieldResult> compareAllFields(Object o1, Object o2, String... ignoreFields);

}
