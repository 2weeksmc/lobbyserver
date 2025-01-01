package com.twoweeksmc.lobbyserver.listener;

import com.twoweeksmc.lobbyserver.util.DualResult;
import net.minestom.server.event.item.ItemDropEvent;

public class ItemDropListener extends GenericListener<ItemDropEvent> {

    @Override
    public DualResult<ItemDropEvent> listen() {
        return new DualResult<>(ItemDropEvent.class, event -> event.setCancelled(true));
    }
}
