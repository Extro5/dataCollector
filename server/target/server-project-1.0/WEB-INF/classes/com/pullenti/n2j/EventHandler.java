package com.pullenti.n2j;


public interface EventHandler<T> {

    void call(Object sender, Object arg);
}
