package com.github.caijh.framework.data.jpa;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TableViewRepository {

    ViewRecord get(View view, long id);

    List<ViewRecord> query(View view, Condition... conditions);

    Page<ViewRecord> page(View view, Pageable pageable, Condition... conditions);

}
