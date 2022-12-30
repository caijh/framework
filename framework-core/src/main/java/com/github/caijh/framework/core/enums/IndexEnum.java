package com.github.caijh.framework.core.enums;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.lang.NonNull;

/**
 * 有索引顺序的enum.
 */
public interface IndexEnum {

    /**
     * Get Enum by index.
     *
     * @param index     index
     * @param typeClass enum implements IndexEnum
     * @param <T>       IndexEnum
     * @return IndexEnum instance
     * @throws NoSuchElementException if index enum not found.
     */
    @NonNull
    static <T extends IndexEnum> T valueOf(int index, Class<T> typeClass) {
        for (T t : typeClass.getEnumConstants()) {
            if (t.getIndex() == index) {
                return t;
            }
        }
        throw new NoSuchElementException();
    }

    static <T extends IndexEnum> List<Integer> indexValues(Class<T> clazz) {
        return Arrays.stream(clazz.getEnumConstants()).map(IndexEnum::getIndex).toList();
    }

    int getIndex();

    default String getDesc() {
        return "";
    }

}
