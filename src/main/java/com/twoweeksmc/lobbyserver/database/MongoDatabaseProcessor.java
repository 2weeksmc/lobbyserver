package com.twoweeksmc.lobbyserver.database;

import java.util.UUID;

import org.bson.Document;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

import de.eztxm.config.JsonConfig;

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

    public MongoClient getMongoClient() {
        return mongoClient;
    }
}
