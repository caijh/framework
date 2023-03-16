package com.github.caijh.framework.data.mybatis.support;

import java.util.function.Supplier;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class FillField {

    private String fieldName;

    private Supplier<?> fieldValue;

}
