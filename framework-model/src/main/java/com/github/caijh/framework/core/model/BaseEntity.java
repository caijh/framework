package com.github.caijh.framework.core.model;

import java.io.Serializable;
import javax.persistence.MappedSuperclass;

import com.vladmihalcea.hibernate.type.array.IntArrayType;
import com.vladmihalcea.hibernate.type.array.StringArrayType;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import com.vladmihalcea.hibernate.type.json.JsonNodeBinaryType;
import com.vladmihalcea.hibernate.type.json.JsonNodeStringType;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import org.hibernate.annotations.TypeDef;

@TypeDef(name = "string-array", typeClass = StringArrayType.class)
@TypeDef(name = "int-array", typeClass = IntArrayType.class)
@TypeDef(name = "json", typeClass = JsonStringType.class)
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
@TypeDef(name = "json-node", typeClass = JsonNodeStringType.class)
@TypeDef(name = "jsonb-node", typeClass = JsonNodeBinaryType.class)
@MappedSuperclass
public abstract class BaseEntity<T extends Serializable> implements PersistentObject<T> {

}
