package com.twoweeksmc.lobbyserver.listener;

import com.twoweeksmc.lobbyserver.database.MongoDatabaseProcessor;
import com.twoweeksmc.lobbyserver.util.DualResult;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.Player;
import net.minestom.server.event.player.AsyncPlayerConfigurationEvent;
import net.minestom.server.instance.InstanceContainer;

public class PlayerConfigurationListener extends GenericListener<AsyncPlayerConfigurationEvent> {
    private final InstanceContainer instanceContainer;
    private final MongoDatabaseProcessor databaseProcessor;

    public PlayerConfigurationListener(InstanceContainer instanceContainer, MongoDatabaseProcessor databaseProcessor) {
        this.instanceContainer = instanceContainer;
        this.databaseProcessor = databaseProcessor;
    }

    @Override
    public DualResult<AsyncPlayerConfigurationEvent> listen() {
        return new DualResult<>(AsyncPlayerConfigurationEvent.class, event -> {
            final Player player = event.getPlayer();
            if (!this.databaseProcessor.addPlayer(player.getUuid())) {
                player.kick(Component.text("Failed to add player", NamedTextColor.RED));
            }
            event.setSpawningInstance(this.instanceContainer);
            player.setRespawnPoint(new Pos(0.5, 68.01, 0.5, 0, 0));
        });
    }
}
