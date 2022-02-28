package com.github.caijh.framework.util;

import java.util.List;

import lombok.Data;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ObjectFieldComparatorUtilsTest {

    @Test
    void compare() {
        User user = new User();
        user.setAge(10);
        user.setName("caijh");
        Person person = new Person();
        person.setName("test");
        List<ComparableFieldResult> results = ObjectFieldComparatorUtils.getObjectFieldComparator().compareComparableField(user, person);
        Assertions.assertTrue(results.stream().anyMatch(e -> !e.isFieldEqual()));
        person.setName("caijh");
        results = ObjectFieldComparatorUtils.getObjectFieldComparator().compareComparableField(user, person);
        Assertions.assertTrue(results.stream().anyMatch(ComparableFieldResult::isFieldEqual));

        user.setStudent(true);
        person.setStudent(true);
        results = ObjectFieldComparatorUtils.getObjectFieldComparator().compareComparableField(user, person);
        Assertions.assertTrue(results.stream().anyMatch(ComparableFieldResult::isFieldEqual));
    }

    @Data
    static class User {

        @ComparableField
        private String name;
        private int age;

        @ComparableField
        private boolean student;

    }

    @Data
    static class Person {

        @ComparableField
        private String name;

        @ComparableField
        private Boolean student;

    }

}
