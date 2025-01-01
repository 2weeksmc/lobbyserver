package com.twoweeksmc.lobbyserver.listener;

import com.twoweeksmc.lobbyserver.util.DualResult;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.minestom.server.entity.GameMode;
import net.minestom.server.entity.Player;
import net.minestom.server.event.player.PlayerSpawnEvent;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;
import net.minestom.server.network.packet.server.play.PlayerListHeaderAndFooterPacket;

public class PlayerSpawnListener extends GenericListener<PlayerSpawnEvent> {

    @Override
    public DualResult<PlayerSpawnEvent> listen() {
        return new DualResult<>(PlayerSpawnEvent.class, event -> {
            final Player player = event.getPlayer();
            PlayerListHeaderAndFooterPacket tablistPacket = new PlayerListHeaderAndFooterPacket(
                    MiniMessage.miniMessage().deserialize("""
                     \s
                     <gradient:#FB1364:#F9A608><bold>2ᴡᴇᴇᴋꜱᴍᴄ.ᴄᴏᴍ</bold></gradient>
                     <#55bbff>%d online
                    \s""".formatted(0)),
                    MiniMessage.miniMessage().deserialize("""
                     \s
                     <#55bbff>Discord <dark_gray>» <#55bbff>2weeksmc.com/dc
                    \s""")
            );
            player.sendPacket(tablistPacket);
            player.setGameMode(GameMode.CREATIVE);
            ItemStack navigator = ItemStack.builder(Material.COMPASS)
                    .customName(Component.text("Navigator", TextColor.fromHexString("#55bbff"))
                            .decoration(TextDecoration.ITALIC, false))
                    .build();
            player.getInventory().setItemStack(4, navigator);
        });
    }
}
