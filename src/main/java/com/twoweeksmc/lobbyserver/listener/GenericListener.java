package com.twoweeksmc.lobbyserver.listener;

import com.twoweeksmc.lobbyserver.util.DualResult;
import java.lang.reflect.ParameterizedType;

public abstract class GenericListener<T> implements IListener<T> {

    @SuppressWarnings("unchecked")
    private Class<T> getTypeClass() {
        return (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass())
                .getActualTypeArguments()[0];
    }

    @Override
    public DualResult<T> listen() {
        return new DualResult<>(getTypeClass(), event -> {

        });
    }
}