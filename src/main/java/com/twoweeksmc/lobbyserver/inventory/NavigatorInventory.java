package com.twoweeksmc.lobbyserver.inventory;

import com.twoweeksmc.lobbyserver.database.MongoDatabaseProcessor;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.minestom.server.entity.Player;
import net.minestom.server.inventory.Inventory;
import net.minestom.server.inventory.InventoryType;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;

public class NavigatorInventory extends Inventory {
    private final MongoDatabaseProcessor databaseProcessor;
    private final Player player;

    public NavigatorInventory(MongoDatabaseProcessor databaseProcessor, Player player) {
        super(InventoryType.CHEST_5_ROW, Component.text("Navigator", TextColor.fromHexString("#55bbff")));
        this.databaseProcessor = databaseProcessor;
        this.player = player;
        this.setItemStack(22, ItemStack.builder(Material.GRASS_BLOCK).build());
    }
}
