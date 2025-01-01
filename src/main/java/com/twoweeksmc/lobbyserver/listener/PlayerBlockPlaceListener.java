package com.twoweeksmc.lobbyserver.listener;

import com.twoweeksmc.lobbyserver.util.DualResult;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.player.PlayerBlockBreakEvent;
import net.minestom.server.event.player.PlayerBlockPlaceEvent;

import java.util.function.Consumer;

public class PlayerBlockPlaceListener extends GenericListener<PlayerBlockPlaceEvent> {

    @Override
    public DualResult<PlayerBlockPlaceEvent> listen() {
        return new DualResult<>(PlayerBlockPlaceEvent.class, event -> event.setCancelled(true));
    }
}
