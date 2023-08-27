package com.github.caijh.framework.core.model.request;

import java.io.Serial;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 抽象列表请求对象.
 */
@Getter
public class PageReqBody implements Serializable {

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

    /**
     * 排序属性.
     */
    private List<Sort> sorts;

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPage() {
        return getPageNo() - 1;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public List<Sort> getSorts() {
        return this.sorts != null ? this.sorts : Collections.emptyList();
    }

    public void setSorts(List<Sort> sorts) {
        this.sorts = sorts;
    }

    public Integer getOffset() {
        return getPage() * this.pageSize;
    }

    public Integer getLimit() {
        return this.pageSize;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Sort implements Serializable {

        @Serial
        private static final long serialVersionUID = 243258760854336905L;
        /**
         * 排序属性.
         */
        private String column;

        /**
         * asc or desc
         */
        private String order;

        private boolean toCamelCase = true;

        public String getColumn() {
            if (!this.toCamelCase) {
                return this.column;
            }

            return this.column.contains("_")
                ? this.column.substring(0, this.column.indexOf("_"))
                + Arrays.stream(this.column.substring(this.column.indexOf("_") + 1).split("_"))
                        .map(s -> Character.toUpperCase(s.charAt(0)) + s.substring(1))
                        .collect(Collectors.joining())
                : this.column;
        }

    }

}
