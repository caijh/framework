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

    /**
     * 根据请求的页码和每页的数量，返回指定分页的数据
     *
     * @param reqBody 请求体，包含页码和每页的数量
     * @return 分页后的数据
     */
    @PostMapping(value = "/list")
    public Page<T> page(@RequestBody @Validated PageReqBody reqBody) {
        PageRequest pageRequest = PageRequest.of(reqBody.getPage(), reqBody.getPageSize());
        return this.listCurdService.findAll(pageRequest);
    }

    /**
     * 保存实体对象到数据库
     *
     * @param entity 实体对象
     * @return 保存后的实体对象
     */
    @PostMapping(value = "")
    public T save(@RequestBody @Validated T entity) {
        return listCurdService.save(entity);
    }

    /**
     * 根据指定id获取指定对象信息
     *
     * @param id 对象的id
     * @return 返回获取到的对象，如果未找到则返回null
     */
    @GetMapping(value = "/{id}")
    public T getById(@PathVariable I id) {
        return listCurdService.getOneOrNull(id);
    }

    /**
     * 根据指定id删除数据
     *
     * @param id 要删除的数据的id
     */
    @DeleteMapping(value = "/{id}")
    public void deleteById(@PathVariable I id) {
        listCurdService.deleteById(id);
    }

    @Inject
    public void setListCurdService(ListCurdService<T, I> listCurdService) {
        this.listCurdService = listCurdService;
    }
}
