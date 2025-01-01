package com.twoweeksmc.lobbyserver.inventory;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.minestom.server.inventory.Inventory;
import net.minestom.server.inventory.InventoryType;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;

public class NavigatorInventory extends Inventory {

    public NavigatorInventory() {
        super(InventoryType.CHEST_5_ROW, Component.text("Navigator", TextColor.fromHexString("#55bbff")));
        this.setItemStack(22, ItemStack.builder(Material.GRASS_BLOCK).build());
    }
}
