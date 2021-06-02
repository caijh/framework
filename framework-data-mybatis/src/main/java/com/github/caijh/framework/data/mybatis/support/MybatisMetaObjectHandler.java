package com.github.caijh.framework.data.mybatis.support;

import java.util.Arrays;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;

public class MybatisMetaObjectHandler implements MetaObjectHandler {

    private final InsertFillFieldProvider insertFillFieldProvider;

    private final UpdateFillFieldProvider updateFillFieldProvider;

    public MybatisMetaObjectHandler(InsertFillFieldProvider insertFillFieldProvider, UpdateFillFieldProvider updateFillFieldProvider) {
        this.insertFillFieldProvider = insertFillFieldProvider;
        this.updateFillFieldProvider = updateFillFieldProvider;
    }


    @Override
    public void insertFill(MetaObject metaObject) {
        Arrays.stream(this.insertFillFieldProvider.getFields())
              .forEach(e -> this.strictInsertFill(metaObject, e.getFieldName(), e.getFieldValue(), e.getFieldClass()));

    }

    @Override
    public void updateFill(MetaObject metaObject) {
        Arrays.stream(this.updateFillFieldProvider.getFields())
              .forEach(e -> this.strictUpdateFill(metaObject, e.getFieldName(), e.getFieldValue(), e.getFieldClass()));
    }

}
