package xyz.apollo30.skyblockremastered.utils;

import com.mongodb.client.*;
import org.bson.Document;
import org.bukkit.entity.Player;
import xyz.apollo30.skyblockremastered.SkyblockRemastered;

public class MongoUtils {

    public static SkyblockRemastered plugin;
    private final MongoCollection<Document> playerCollection;

    public MongoUtils(SkyblockRemastered plugin, String conn, String databaseName, String collectionName) {
        MongoUtils.plugin = plugin;
        MongoClient client = MongoClients.create(conn);
        MongoDatabase database = client.getDatabase(databaseName);
        playerCollection = database.getCollection(collectionName);
    }

    public MongoCollection<Document> getPlayerCollection() {
        return playerCollection;
    }

    public static MongoUtils getInstance() {
        return plugin.playerData;
    }

    public void setPlayerData(Player plr, String path, String value) {
        FindIterable<Document> playerDoc = playerCollection.find(new Document("uuid", plr.getUniqueId().toString()));
        if (playerDoc == null) {

        }
    }

}