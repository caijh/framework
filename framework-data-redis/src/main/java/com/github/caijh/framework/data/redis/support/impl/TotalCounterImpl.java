package com.github.caijh.framework.data.redis.support.impl;

import com.github.caijh.framework.data.redis.support.TotalCounter;
import lombok.Data;

@Data
public class TotalCounterImpl implements TotalCounter {

    private boolean calcTotal = false;

    private long total;

    @Override
    public boolean calcTotal() {
        return calcTotal;
    }

}
