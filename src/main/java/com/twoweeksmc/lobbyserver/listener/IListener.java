package com.twoweeksmc.lobbyserver.listener;

import com.twoweeksmc.lobbyserver.util.DualResult;

public interface IListener<T> {
    DualResult<T> listen();
}
