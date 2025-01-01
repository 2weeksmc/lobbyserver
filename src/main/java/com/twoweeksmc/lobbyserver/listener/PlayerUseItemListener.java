package com.twoweeksmc.lobbyserver.listener;

import com.twoweeksmc.lobbyserver.util.DualResult;
import net.minestom.server.entity.Player;
import net.minestom.server.event.player.PlayerUseItemEvent;
import net.minestom.server.inventory.Inventory;

public class PlayerUseItemListener extends GenericListener<PlayerUseItemEvent> {
    private final Inventory navigatorInventory;

    public PlayerUseItemListener(Inventory navigatorInventory) {
        this.navigatorInventory = navigatorInventory;
    }

    @Override
    public DualResult<PlayerUseItemEvent> listen() {
        return new DualResult<>(PlayerUseItemEvent.class, event -> {
            final Player player = event.getPlayer();
            player.openInventory(this.navigatorInventory);
        });
    }
}
