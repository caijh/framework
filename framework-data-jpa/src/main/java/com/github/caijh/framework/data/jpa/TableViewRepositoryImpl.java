package com.github.caijh.framework.data.jpa;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;

import com.github.caijh.framework.data.jpa.annotation.Relation;
import com.github.caijh.framework.data.jpa.annotation.TableView;
import org.apache.logging.log4j.util.Strings;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public class TableViewRepositoryImpl implements TableViewRepository {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public ViewRecord get(View view, long id) {
        return null;
    }

    @Override
    public List<ViewRecord> query(View view, Condition... conditions) {
        Class<? extends View> clazz = view.getClass();
        if (!clazz.isAnnotationPresent(TableView.class)) {
            throw new TableViewParseException("View Record must annotation by @TableView");
        }
        TableView tableView = clazz.getAnnotation(TableView.class);
        Class<?> base = tableView.base();
        Relation[] join = tableView.relations();
        StringBuilder fromBuilder = new StringBuilder();
        for (Relation relation : join) {
            Class<?> relationBase = relation.base();
            if (relationBase == Relation.RelationBaseNull.class) {
                relationBase = base;
            }

            fromBuilder.append(" ").append(relationBase.getName()).append(" ");
            JoinType joinType = relation.joinType();
            fromBuilder.append(joinType.sqlString()).append(" ").append(relation.reference().getName()).append(relation.on());
        }
        String fromSqlString = fromBuilder.toString();
        StringBuilder hql = new StringBuilder();
        String fieldsSqlString = exactFieldsSql(base);
        hql.append("select ")
           .append(fieldsSqlString)
           .append(" from ").append(fromSqlString);
        String s = hql.toString();
        //        return sessionFactory.getCurrentSession().createQuery(s, view.getClass()).list();
        return Collections.emptyList();
    }

    private String exactFieldsSql(Class<?> base) {
        Field[] declaredFields = base.getDeclaredFields();
        StringBuilder sql = new StringBuilder();
        if (declaredFields.length > 0) {
            for (Field declaredField : declaredFields) {
                if (!declaredField.isAnnotationPresent(com.github.caijh.framework.data.jpa.annotation.Field.class)) {
                    continue;
                }
                com.github.caijh.framework.data.jpa.annotation.Field field = declaredField.getAnnotation(com.github.caijh.framework.data.jpa.annotation.Field.class);
                Class<?> clazz = field.clazz();
                sql.append(clazz.getName())
                   .append(".")
                   .append(Strings.isNotBlank(field.field()) ? field.field() : declaredField.getName()).append(",");
            }
            String s = sql.toString();
            return s.substring(0, s.length() - 1);
        }
        sql.append("*");
        return sql.toString();
    }

    @Override
    public Page<ViewRecord> page(View view, Pageable pageable, Condition... conditions) {
        return null;
    }

}
