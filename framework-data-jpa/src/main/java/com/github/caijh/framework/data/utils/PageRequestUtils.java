package com.github.caijh.framework.data.utils;

import java.util.List;
import java.util.stream.Collectors;

import com.github.caijh.framework.core.model.PageReqBody;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

public class PageRequestUtils {

    private PageRequestUtils() {
    }

    public static PageRequest newPageRequest(PageReqBody pageReqBody) {
        List<Sort.Order> orders = pageReqBody.getSorts().stream()
                                             .map(e -> new Sort.Order(Sort.Direction.fromString(e.getOrder()), e.getColumn()))
                                             .collect(Collectors.toList());
        return PageRequest.of(pageReqBody.getPageNo(), pageReqBody.getPageSize(), Sort.by(orders));
    }

}
