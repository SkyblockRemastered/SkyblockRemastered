package xyz.apollo30.skyblockremastered.utils;

import com.mongodb.client.*;
import org.bson.Document;
import org.bukkit.entity.Player;
import xyz.apollo30.skyblockremastered.SkyblockRemastered;

public class MongoUtils {

    private final MongoCollection<Document> playerCollection;
    private static MongoUtils instance;

    public MongoUtils(String conn, String databaseName, String collectionName) {
        instance = this;
        MongoClient client = MongoClients.create(conn);
        MongoDatabase database = client.getDatabase(databaseName);
        playerCollection = database.getCollection(collectionName);
    }

    public MongoCollection<Document> getPlayerCollection() {
        return playerCollection;
    }

    public static MongoUtils getInstance() {
        return instance;
    }


    public static void insertOrUpdate(Document identifier, Document doc) {
        if (getInstance().getPlayerCollection().find(identifier).first() == null) {
            getInstance().getPlayerCollection().insertOne(doc);
        } else {
            getInstance().getPlayerCollection().replaceOne(identifier, doc);
        }
    }




}
