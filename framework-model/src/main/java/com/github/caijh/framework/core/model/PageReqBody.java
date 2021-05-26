package com.github.caijh.framework.core.model;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import lombok.Data;

/**
 * 抽象列表请求对象.
 */
public class PageReqBody implements ReqBody {

    public static final int DEFAULT_PAGE_NO = 1;
    public static final int DEFAULT_PAGE_SIZE = 10;

    /**
     * 页码，默认1.
     */
    private Integer pageNo = PageReqBody.DEFAULT_PAGE_NO;

    /**
     * 每页大小，默认10.
     */
    private Integer pageSize = PageReqBody.DEFAULT_PAGE_SIZE;

    private List<Sort> sorts;

    public Integer getPageNo() {
        return this.pageNo - 1;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public List<Sort> getSorts() {
        return this.sorts != null ? this.sorts : Collections.emptyList();
    }

    @Data
    public static class Sort implements Serializable {

        private static final long serialVersionUID = 243258760854336905L;
        private String column;
        /**
         * asc or desc
         */
        private String order;

    }

}
