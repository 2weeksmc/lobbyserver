package com.twoweeksmc.lobbyserver.inventory;

import org.bson.Document;

import com.twoweeksmc.connector.MongoConnector;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.minestom.server.entity.Player;
import net.minestom.server.inventory.Inventory;
import net.minestom.server.inventory.InventoryType;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;

public class NavigatorInventory extends Inventory {
    private final MongoConnector mongoConnector;
    private final Player player;

    public NavigatorInventory(MongoConnector mongoConnector, Player player) {
        super(InventoryType.CHEST_5_ROW, Component.text("Navigator", TextColor.fromHexString("#55bbff")));
        this.mongoConnector = mongoConnector;
        this.player = player;
        Document playerData = this.mongoConnector.getPlayerModel().getPlayer(this.player.getUuid());
        if (playerData.get("last-server-end") != null /* TODO: or timestamp lower than 3 months */) {
            this.setItemStack(22, ItemStack.builder(Material.BARRIER)
                    .customName(Component.text("Server on cooldown", TextColor.fromHexString("#ff2222"))
                            .decoration(TextDecoration.ITALIC, false))
                    .build());
            return;
        }
        Document playerServerData = (Document) playerData.get("server");
        if (playerServerData != null) {
            this.setItemStack(22, ItemStack.builder(Material.GRASS_BLOCK)
                    .customName(Component.text("Join server", TextColor.fromHexString("#55be55"))
                            .decoration(TextDecoration.ITALIC, false))
                    .build());
            return;
        }
        this.setItemStack(22, ItemStack.builder(Material.COMMAND_BLOCK)
                .customName(Component.text("Create server", TextColor.fromHexString("#55be55"))
                        .decoration(TextDecoration.ITALIC, false))
                .build());
    }
}
