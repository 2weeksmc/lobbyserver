package com.twoweeksmc.lobbyserver.util;

import java.util.function.Consumer;

public record DualResult<T>(Class<T> clazz, Consumer<T> consumer) {
}

