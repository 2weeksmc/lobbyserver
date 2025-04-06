package com.twoweeksmc.lobbyserver.server;

import com.nexoscript.dsm.common.server.container.ServerContainer;
import com.nexoscript.dsm.common.server.manager.ServerManager;
import de.eztxm.ezlib.config.object.JsonArray;
import de.eztxm.ezlib.config.object.JsonObject;

import java.util.List;
import java.util.UUID;

public final class Server {
    private final UUID uniqueId;
    private final ServerManager serverManager;
    private final int gemsPrice;
    private final int weeks;
    private final int cooldownWeeks;
    private final int maxPlayers;
    private final int maxMemory;
    private final JsonArray<String> plugins;
    private String id;

    public Server(ServerManager serverManager, int gemsPrice, int weeks, int cooldownWeeks, int maxPlayers, int maxMemory, JsonArray<String> plugins) {
        this(UUID.randomUUID(), serverManager, gemsPrice, weeks, cooldownWeeks, maxPlayers, maxMemory, plugins);
    }

    public Server(UUID uniqueId, ServerManager serverManager, int gemsPrice, int weeks, int cooldownWeeks, int maxPlayers, int maxMemory, JsonArray<String> plugins) {
        this.uniqueId = uniqueId;
        this.serverManager = serverManager;
        this.gemsPrice = gemsPrice;
        this.weeks = weeks;
        this.cooldownWeeks = cooldownWeeks;
        this.maxPlayers = maxPlayers;
        this.maxMemory = maxMemory;
        this.plugins = plugins;
    }

    public void create() {
        ServerContainer container = this.serverManager.createServerContainer("PURPUR", "1.21.4", this.maxMemory);
        this.id = container.getName();
    }

    public void start() {
        this.serverManager.startServerContainer(this.id);
    }

    public void stop() {
        this.serverManager.stopServerContainer(this.id);
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        json.put("uniqueId", this.uniqueId.toString());
        json.put("gemsPrice", this.gemsPrice);
        json.put("weeks", this.weeks);
        json.put("cooldownWeeks", this.cooldownWeeks);
        json.put("maxPlayers", this.maxPlayers);
        json.put("maxMemory", this.maxMemory);
        json.put("plugins", this.plugins);
        json.put("id", this.id);
        return json;
    }

    public static Server fromJson(JsonObject jsonObject, ServerManager serverManager) {
        UUID uniqueId = UUID.fromString(jsonObject.getConverted("uniqueId").asString());
        int gemsPrice = jsonObject.getConverted("gemsPrice").asInteger();
        int weeks = jsonObject.getConverted("weeks").asInteger();
        int cooldownWeeks = jsonObject.getConverted("cooldownWeeks").asInteger();
        int maxPlayers = jsonObject.getConverted("maxPlayers").asInteger();
        int maxMemory = jsonObject.getConverted("maxMemory").asInteger();
        JsonArray<String> plugins = (JsonArray<String>) jsonObject.getConverted("plugins").asJsonArray();
        Server server = new Server(uniqueId, serverManager, gemsPrice, weeks, cooldownWeeks, maxPlayers, maxMemory, plugins);
        server.id = jsonObject.getConverted("docker-name").asString();
        return server;
    }

    public UUID getUniqueId() {
        return uniqueId;
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

    public String getId() {
        return id;
    }
}
