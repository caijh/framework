package com.github.caijh.framework.util;

import java.util.Map;

import com.github.caijh.commons.util.Maps;
import lombok.Data;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class XmlUtilsTest {

    @Test
    void toXml() {
        Person p = new Person();
        p.name = "a";
        String xml = XmlUtils.toXml(p);
        System.out.println(xml);
        Map<String, Object> map = Maps.fromXml(xml);
        Assertions.assertEquals("a", Maps.getValue(map, "person.name"));
    }

    @Test
    void fromXml() {
        Person p = new Person();
        p.name = "a";
        String xml = XmlUtils.toXml(p);
        System.out.println(xml);
        Person person = XmlUtils.fromXml(xml, Person.class);
        Assertions.assertEquals(person.getName(), p.name);
    }

    @Data
//    @XmlRootElement
    public static class Person {

        private String name;

    }

}
