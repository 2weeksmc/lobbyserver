package com.twoweeksmc.lobbyserver.server;

import java.util.List;

public class Server {
    private final int weeks;
    private final int maxPlayers;
    private final int maxMemory;
    private final List<String> plugins;

    public Server(int weeks, int maxPlayers, int maxMemory, List<String> plugins) {
        this.weeks = weeks;
        this.maxPlayers = maxPlayers;
        this.maxMemory = maxMemory;
        this.plugins = plugins;
    }

    public static Server getFromTemplate(ServerTemplate template) {
        return new Server(template.getWeeks(), template.getMaxPlayers(), template.getMaxMemory(),
                template.getPlugins());
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
