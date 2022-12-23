package com.github.caijh.framework.data.util;

import java.util.List;

import com.github.caijh.framework.core.model.request.PageReqBody;
import jakarta.annotation.Nonnull;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

public class PageRequestUtils {

    private PageRequestUtils() {
    }

    public static PageRequest newPageRequest(@Nonnull PageReqBody pageReqBody) {
        List<Sort.Order> orders = pageReqBody.getSorts().stream()
                                             .map(e -> new Sort.Order(Sort.Direction.fromString(e.getOrder()), e.getColumn()))
                                             .toList();
        return PageRequest.of(pageReqBody.getPage(), pageReqBody.getPageSize(), Sort.by(orders));
    }

}
