package com.twoweeksmc.lobbyserver.util;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import net.minestom.server.inventory.Inventory;

public class PlayerManager {
    private final Map<UUID, Map<String, Inventory>> playerInventories;

    public PlayerManager() {
        this.playerInventories = new HashMap<>();
    }

    public Map<UUID, Map<String, Inventory>> getPlayerInventories() {
        return playerInventories;
    }
}
