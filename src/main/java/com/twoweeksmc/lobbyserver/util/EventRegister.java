package com.twoweeksmc.lobbyserver.util;

import net.minestom.server.event.Event;
import net.minestom.server.event.GlobalEventHandler;

public class EventRegister {
    private final GlobalEventHandler eventHandler;

    public EventRegister(GlobalEventHandler eventHandler) {
        this.eventHandler = eventHandler;
    }

    public <T extends Event> void register(DualResult<T> listener) {
        this.eventHandler.addListener(listener.clazz(), listener.consumer());
    }
}
