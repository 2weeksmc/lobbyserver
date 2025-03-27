package com.twoweeksmc.lobbyserver.database;

import java.time.Instant;
import java.util.UUID;

import org.bson.Document;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.twoweeksmc.lobbyserver.server.Server;

import de.eztxm.ezlib.config.JsonConfig;

public class MongoDatabaseProcessor {
    private final JsonConfig databaseConfiguration;
    private final MongoClient mongoClient;
    private final MongoDatabase mongoDatabase;
    private final MongoCollection<Document> serverCollection;
    private final MongoCollection<Document> playerCollection;

    public MongoDatabaseProcessor(JsonConfig databaseConfiguration) {
        this.databaseConfiguration = databaseConfiguration;
        this.mongoClient = MongoClients.create(this.databaseConfiguration.get("url").asString());
        this.mongoDatabase = this.mongoClient.getDatabase(this.databaseConfiguration.get("database").asString());
        this.serverCollection = this.mongoDatabase.getCollection("servers");
        this.playerCollection = this.mongoDatabase.getCollection("players");
    }

    public boolean addServer(UUID ownerId, Server server) {
        if (this.getServer(ownerId) != null) {
            return true;
        }
        Document document = new Document();
        document.put("ownerId", ownerId.toString());
        document.put("start", Instant.now());
        document.put("weeks", server.getWeeks());
        document.put("max-players", server.getMaxPlayers());
        document.put("max-memory", server.getMaxMemory());
        document.put("plugins", server.getPlugins());
        return this.serverCollection.insertOne(document).wasAcknowledged();
    }

    public boolean addPlayer(UUID uuid) {
        if (this.getPlayer(uuid) != null) {
            return true;
        }
        Document document = new Document();
        document.put("uuid", uuid.toString());
        document.put("gems", 50);
        document.put("last-server-end", null);
        document.put("server", null);
        return this.playerCollection.insertOne(document).wasAcknowledged();
    }

    public Document getPlayer(UUID uuid) {
        return this.playerCollection.find(Filters.eq("uuid", uuid.toString())).first();
    }

    public Document getServer(UUID ownerId) {
        return this.serverCollection.find(Filters.eq("ownerId", ownerId.toString())).first();
    }

    public MongoClient getMongoClient() {
        return mongoClient;
    }
}
