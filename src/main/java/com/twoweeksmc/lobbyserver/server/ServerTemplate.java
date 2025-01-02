package com.twoweeksmc.lobbyserver.server;

import java.util.ArrayList;
import java.util.List;

public enum ServerTemplate {
    DEFAULT_SURVIVAL(0, 2, 12, 4, 2048, new ArrayList<>(List.of("2weeksmc-core"))),
    DEFAULT_BUILDING(0, 2, 10, 2, 2048, new ArrayList<>(List.of(
            "2weeksmc-core", "fastasyncworldedit", "voxelsniper"))),
    PREMIUM_SURVIVAL(50, 4, 8, 4, 4096, new ArrayList<>(List.of(
            "2weeksmc-core", "2weeksmc-survival-premium"))),
    PREMIUM_BUILDING(50, 4, 6, 4, 4096, new ArrayList<>(List.of(
            "2weeksmc-core", "fastasyncworldedit", "voxelsniper", "gobrush", "gopaint"))),
    ENTERPRISE_SURVIVAL(100, 8, 4, 8, 6144, new ArrayList<>(List.of(
            "2weeksmc-core",
            "2weeksmc-survival-enterprise"))),
    ENTERPRISE_BUILDING(100, 8, 4, 8,
            6144, new ArrayList<>(List.of(
                    "2weeksmc-core", "fastasyncworldedit", "voxelsniper", "gobrush", "gopaint", "craftflowers",
                    "armostandtools")));

    private final int gemsPrice;
    private final int weeks;
    private final int cooldownWeeks;
    private final int maxPlayers;
    private final int maxMemory;
    private final List<String> plugins;

    ServerTemplate(int gemsPrice, int weeks, int cooldownWeeks, int maxPlayers, int maxMemory, List<String> plugins) {
        this.gemsPrice = gemsPrice;
        this.weeks = weeks;
        this.cooldownWeeks = cooldownWeeks;
        this.maxPlayers = maxPlayers;
        this.maxMemory = maxMemory;
        this.plugins = plugins;
    }

    public List<String> getPlugins() {
        return plugins;
    }

    public int getMaxMemory() {
        return maxMemory;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public int getCooldownWeeks() {
        return cooldownWeeks;
    }

    public int getWeeks() {
        return weeks;
    }

    public int getGemsPrice() {
        return gemsPrice;
    }
}