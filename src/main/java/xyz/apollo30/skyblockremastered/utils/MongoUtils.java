//package xyz.apollo30.skyblockremastered.utils;
//
//import com.mongodb.client.*;
//import com.mongodb.client.internal.MongoClientImpl;
//import org.bson.Document;
//import org.bukkit.entity.Player;
//import xyz.apollo30.skyblockremastered.SkyblockRemastered;
//
//public class MongoUtils {
//
//    private static SkyblockRemastered plugin;
//    private MongoClient client;
//    private MongoDatabase database;
//    private MongoCollection<Document> playerCollection;
//
//    public MongoUtils(SkyblockRemastered plugin, String conn, String databaseName, String collectionName) {
//        MongoUtils.plugin = plugin;
//        client = MongoClients.create(conn);
//        database = client.getDatabase(databaseName);
//        playerCollection = database.getCollection(collectionName);
//    }
//
//    public MongoCollection<Document> getPlayerCollection() {
//        return playerCollection;
//    }
//
//    public static MongoUtils getInstance() {
//        return plugin.mongoUtils;
//    }
//
//        public void setPlayerData(Player plr, String path, String value) {
//        FindIterable<Document> playerDoc = playerCollection.find(new Document("uuid", plr.getUniqueId().toString()));
//        if (playerDoc == null) {
//
//        }
//    }
//
//}