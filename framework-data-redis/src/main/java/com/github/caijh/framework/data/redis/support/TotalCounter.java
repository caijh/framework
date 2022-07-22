package com.github.caijh.framework.data.redis.support;

public interface TotalCounter {

    long getTotal();

    void setTotal(long total);

    boolean calcTotal();

    void setCalcTotal(boolean b);

}
