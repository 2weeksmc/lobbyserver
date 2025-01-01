package com.twoweeksmc.lobbyserver.listener;

import com.twoweeksmc.lobbyserver.util.DualResult;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.Player;
import net.minestom.server.event.player.PlayerMoveEvent;

public class PlayerMoveListener extends GenericListener<PlayerMoveEvent> {

    @Override
    public DualResult<PlayerMoveEvent> listen() {
        return new DualResult<>(PlayerMoveEvent.class, event -> {
            final Player player = event.getPlayer();
            if (player.getPosition().y() <= 40) {
                player.teleport(new Pos(0.5, 68.01, 0.5, 0, 0));
            }
        });
    }
}
