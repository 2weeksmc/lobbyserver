package com.twoweeksmc.lobbyserver.listener;

import com.twoweeksmc.lobbyserver.util.DualResult;
import net.minestom.server.event.player.PlayerBlockBreakEvent;

public class PlayerBlockBreakListener extends GenericListener<PlayerBlockBreakEvent> {

    @Override
    public DualResult<PlayerBlockBreakEvent> listen() {
        return new DualResult<>(PlayerBlockBreakEvent.class, event -> event.setCancelled(true));
    }
}
