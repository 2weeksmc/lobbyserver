package com.twoweeksmc.lobbyserver.listener;

import com.twoweeksmc.lobbyserver.util.DualResult;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.Player;
import net.minestom.server.event.player.AsyncPlayerConfigurationEvent;
import net.minestom.server.instance.InstanceContainer;

public class PlayerConfigurationListener extends GenericListener<AsyncPlayerConfigurationEvent> {
    private final InstanceContainer instanceContainer;

    public PlayerConfigurationListener(InstanceContainer instanceContainer) {
        this.instanceContainer = instanceContainer;
    }

    @Override
    public DualResult<AsyncPlayerConfigurationEvent> listen() {
        return new DualResult<>(AsyncPlayerConfigurationEvent.class, event -> {
            final Player player = event.getPlayer();
            event.setSpawningInstance(this.instanceContainer);
            player.setRespawnPoint(new Pos(0.5, 68.01, 0.5, 0, 0));
        });
    }
}
