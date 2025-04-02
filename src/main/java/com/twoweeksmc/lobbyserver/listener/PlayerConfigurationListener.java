package com.twoweeksmc.lobbyserver.listener;

import java.util.HashMap;
import java.util.Map;

import com.twoweeksmc.connector.MongoConnector;
import com.twoweeksmc.lobbyserver.inventory.NavigatorInventory;
import com.twoweeksmc.lobbyserver.util.DualResult;
import com.twoweeksmc.lobbyserver.util.PlayerManager;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.Player;
import net.minestom.server.event.player.AsyncPlayerConfigurationEvent;
import net.minestom.server.instance.InstanceContainer;
import net.minestom.server.inventory.Inventory;

public class PlayerConfigurationListener extends GenericListener<AsyncPlayerConfigurationEvent> {
    private final InstanceContainer instanceContainer;
    private final MongoConnector mongoConnector;
    private final PlayerManager playerManager;

    public PlayerConfigurationListener(InstanceContainer instanceContainer, MongoConnector mongoConnector,
            PlayerManager playerManager) {
        this.instanceContainer = instanceContainer;
        this.mongoConnector = mongoConnector;
        this.playerManager = playerManager;
    }

    @Override
    public DualResult<AsyncPlayerConfigurationEvent> listen() {
        return new DualResult<>(AsyncPlayerConfigurationEvent.class, event -> {
            final Player player = event.getPlayer();
            if (!this.mongoConnector.getPlayerModel().addPlayer(player.getUuid())) {
                player.kick(Component.text("Failed to add player", NamedTextColor.RED));
            }
            Map<String, Inventory> inventories = new HashMap<>();
            inventories.put("navigator", new NavigatorInventory(this.mongoConnector, player));
            this.playerManager.getPlayerInventories().put(player.getUuid(), inventories);
            event.setSpawningInstance(this.instanceContainer);
            player.setRespawnPoint(new Pos(0.5, 68.01, 0.5, 0, 0));
        });
    }
}
