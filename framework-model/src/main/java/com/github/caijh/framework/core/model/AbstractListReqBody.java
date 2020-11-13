package com.github.caijh.framework.core.model;

/**
 * 抽象列表请求对象.
 */
public abstract class AbstractListReqBody implements ReqBody {

    public static final int DEFAULT_PAGE_NO = 1;
    public static final int DEFAULT_PAGE_SIZE = 10;

    /**
     * 页码，默认1.
     */
    private Integer pageNo = DEFAULT_PAGE_NO;

    /**
     * 每页大小，默认10.
     */
    private Integer pageSize = DEFAULT_PAGE_SIZE;

    public Integer getPageNo() {
        return this.pageNo - 1;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

}
