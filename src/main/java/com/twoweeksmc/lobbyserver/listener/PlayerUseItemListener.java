package com.twoweeksmc.lobbyserver.listener;

import com.twoweeksmc.lobbyserver.util.DualResult;
import com.twoweeksmc.lobbyserver.util.PlayerManager;

import net.minestom.server.entity.Player;
import net.minestom.server.event.player.PlayerUseItemEvent;

public class PlayerUseItemListener extends GenericListener<PlayerUseItemEvent> {
    private final PlayerManager playerManager;

    public PlayerUseItemListener(PlayerManager playerManager) {
        this.playerManager = playerManager;
    }

    @Override
    public DualResult<PlayerUseItemEvent> listen() {
        return new DualResult<>(PlayerUseItemEvent.class, event -> {
            final Player player = event.getPlayer();
            player.openInventory(this.playerManager.getPlayerInventories().get(player.getUuid()).get("navigator"));
        });
    }
}
