package com.github.caijh.framework.data.jpa;

import java.util.List;

import com.github.caijh.framework.data.jpa.annotation.Relation;
import com.github.caijh.framework.data.jpa.annotation.TableView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public class TableViewRepositoryImpl implements TableViewRepository {

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
            fromBuilder.append(joinType.sqlString()).append(" ").append(relation.on());
        }
        String viewString = fromBuilder.toString();
        StringBuilder sql = new StringBuilder();
        sql.append("select * view " + viewString);
        return null;
    }

    @Override
    public Page<ViewRecord> page(View view, Pageable pageable, Condition... conditions) {
        return null;
    }

}
