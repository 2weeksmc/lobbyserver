package com.twoweeksmc.lobbyserver.listener;

import com.twoweeksmc.lobbyserver.util.DualResult;
import net.minestom.server.entity.Player;
import net.minestom.server.event.inventory.InventoryPreClickEvent;
import net.minestom.server.inventory.Inventory;
import net.minestom.server.item.Material;

import java.util.Objects;

public class InventoryPreClickListener extends GenericListener<InventoryPreClickEvent> {
    private final Inventory navigatorInventory;

    public InventoryPreClickListener(Inventory navigatorInventory) {
        this.navigatorInventory = navigatorInventory;
    }

    @Override
    public DualResult<InventoryPreClickEvent> listen() {
        return new DualResult<>(InventoryPreClickEvent.class, event -> {
            event.setCancelled(true);
            final Player player = event.getPlayer();
            if (Objects.equals(event.getInventory(), navigatorInventory)) {
                if (event.getClickedItem().material() == Material.GRASS_BLOCK) {
                    player.sendMessage("You clicked a block!");
                    return;
                }
                return;
            }
        });
    }
}
