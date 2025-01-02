package com.twoweeksmc.lobbyserver.listener;

import java.util.Objects;

import com.twoweeksmc.lobbyserver.util.DualResult;
import com.twoweeksmc.lobbyserver.util.PlayerManager;

import net.minestom.server.entity.Player;
import net.minestom.server.event.inventory.InventoryPreClickEvent;
import net.minestom.server.inventory.Inventory;
import net.minestom.server.item.Material;

public class InventoryPreClickListener extends GenericListener<InventoryPreClickEvent> {
    private final PlayerManager playerManager;

    public InventoryPreClickListener(PlayerManager playerManager) {
        this.playerManager = playerManager;
    }

    @Override
    public DualResult<InventoryPreClickEvent> listen() {
        return new DualResult<>(InventoryPreClickEvent.class, event -> {
            event.setCancelled(true);
            final Player player = event.getPlayer();
            Inventory navigatorInventory = playerManager.getPlayerInventories().get(player.getUuid()).get("navigator");
            if (navigatorInventory != null && Objects.requireNonNull(event.getInventory()).equals(navigatorInventory)) {
                if (event.getClickedItem().material() == Material.GRASS_BLOCK) {
                    player.sendMessage("You clicked a block!");
                    return;
                }
                return;
            }
            player.sendMessage("not valid");
        });
    }
}
