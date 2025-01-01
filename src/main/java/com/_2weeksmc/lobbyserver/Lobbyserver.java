package com._2weeksmc.lobbyserver;

import de.eztxm.ezlib.config.JsonConfig;
import net.minestom.server.MinecraftServer;

public class Lobbyserver {
    private final JsonConfig jsonConfig;

    public static void main(String[] args) {
        new Lobbyserver();
    }

    public Lobbyserver() {
        this.jsonConfig = new JsonConfig(".", "server.json");
        this.jsonConfig.addDefault("host", "0.0.0.0");
        this.jsonConfig.addDefault("port", 25590);
        MinecraftServer server = MinecraftServer.init();
        
        server.start(this.jsonConfig.get("host").asString(), this.jsonConfig.get("port").asInteger());
    }

    public JsonConfig getJsonConfig() {
        return jsonConfig;
    }
}