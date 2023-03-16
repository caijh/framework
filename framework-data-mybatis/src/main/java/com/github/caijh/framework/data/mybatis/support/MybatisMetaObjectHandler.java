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

    @SuppressWarnings("unchecked")
    @Override
    public void insertFill(MetaObject metaObject) {
        Arrays.stream(this.insertFillFieldProvider.getFields())
              .forEach(e -> {
                  Object value = this.getFieldValByName(e.getFieldName(), metaObject);
                  if (value == null) {
                      Object newValue = e.getFieldValue().get();
                      this.strictInsertFill(metaObject, e.getFieldName(), (Class) newValue.getClass(), newValue);
                  }
              });
    }

    @SuppressWarnings("unchecked")
    @Override
    public void updateFill(MetaObject metaObject) {
        Arrays.stream(this.updateFillFieldProvider.getFields())
              .forEach(e -> {
                  Object value = e.getFieldValue().get();
                  this.strictUpdateFill(metaObject, e.getFieldName(), (Class) value.getClass(), value);
              });
    }

}
