package com.twoweeksmc.lobbyserver.server;

import java.util.ArrayList;
import java.util.List;

public enum ServerTemplate {
    DEFAULT_SURVIVAL(2, 4, 2048, new ArrayList<>(List.of("2weeksmc-core"))),
    DEFAULT_BUILDING(2, 2, 2048, new ArrayList<>(List.of(
            "2weeksmc-core", "fastasyncworldedit", "voxelsniper"))),
    PREMIUM_SURVIVAL(4, 4, 4096, new ArrayList<>(List.of(
            "2weeksmc-core", "2weeksmc-survival-premium"))),
    PREMIUM_BUILDING(4, 4, 4096, new ArrayList<>(List.of(
            "2weeksmc-core", "fastasyncworldedit", "voxelsniper", "gobrush", "gopaint"))),
    ENTERPRISE_SURVIVAL(8, 8, 6144, new ArrayList<>(List.of(
            "2weeksmc-core",
            "2weeksmc-survival-enterprise"))),
    ENTERPRISE_BUILDING(8, 8,
            6144, new ArrayList<>(List.of(
                    "2weeksmc-core", "fastasyncworldedit", "voxelsniper", "gobrush", "gopaint", "craftflowers",
                    "armostandtools")));

    private final int weeks;
    private final int maxPlayers;
    private final int maxMemory;
    private final List<String> plugins;

    ServerTemplate(int weeks, int maxPlayers, int maxMemory, List<String> plugins) {
        this.weeks = weeks;
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

    public int getWeeks() {
        return weeks;
    }
}