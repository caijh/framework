package com.github.caijh.framework.web.controller;

import com.github.caijh.framework.core.model.request.PageReqBody;
import com.github.caijh.framework.core.service.ListCurdService;
import jakarta.inject.Inject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public abstract class CrudController<T, I> extends BaseController {
    private ListCurdService<T, I> listCurdService;

    @PostMapping(value = "/list")
    public Page<T> page(@RequestBody @Validated PageReqBody reqBody) {
        PageRequest pageRequest = PageRequest.of(reqBody.getPage(), reqBody.getPageSize());
        return this.listCurdService.findAll(pageRequest);
    }

    @PostMapping(value = "")
    public T save(@RequestBody @Validated T entity) {
        return listCurdService.save(entity);
    }

    @GetMapping(value = "/{id}")
    public T getById(@PathVariable I id) {
        return listCurdService.getOneOrNull(id);
    }

    @DeleteMapping(value = "/{id}")
    public void deleteById(@PathVariable I id) {
        listCurdService.deleteById(id);
    }

    @Inject
    public void setListCurdService(ListCurdService<T, I> listCurdService) {
        this.listCurdService = listCurdService;
    }
}
