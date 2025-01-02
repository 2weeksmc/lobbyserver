package com.twoweeksmc.lobbyserver.server;

import java.util.List;

public class Server {
    private final int gemsPrice;
    private final int weeks;
    private final int cooldownWeeks;
    private final int maxPlayers;
    private final int maxMemory;
    private final List<String> plugins;

    public Server(int gemsPrice, int weeks, int cooldownWeeks, int maxPlayers, int maxMemory, List<String> plugins) {
        this.gemsPrice = gemsPrice;
        this.weeks = weeks;
        this.cooldownWeeks = cooldownWeeks;
        this.maxPlayers = maxPlayers;
        this.maxMemory = maxMemory;
        this.plugins = plugins;
    }

    public void start() {
        // TODO: integration of servermanager api to start a server
    }

    public static Server getFromTemplate(ServerTemplate template) {
        return new Server(template.getGemsPrice(), template.getWeeks(), template.getCooldownWeeks(),
                template.getMaxPlayers(), template.getMaxMemory(),
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
