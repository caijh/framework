package com.github.caijh.framework.core.enums;

import java.util.NoSuchElementException;
import javax.annotation.Nonnull;

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
     */
    @Nonnull
    static <T extends IndexEnum> T valueOf(int index, Class<T> typeClass) {
        for (T t : typeClass.getEnumConstants()) {
            if (t.getIndex() == index) {
                return t;
            }
        }
        throw new NoSuchElementException();
    }

    int getIndex();

}
