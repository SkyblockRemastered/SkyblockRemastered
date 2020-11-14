package xyz.apollo30.skyblockremastered.utils;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class MongoUtils {

    private final MongoCollection<Document> playerCollection, keyCollection;
    private static MongoUtils instance;

    public MongoUtils(String conn, String databaseName, String playerCollectionName, String keyCollectionName) {
        instance = this;
        MongoClient client = MongoClients.create(conn);
        MongoDatabase database = client.getDatabase(databaseName);
        playerCollection = database.getCollection(playerCollectionName);
        keyCollection = database.getCollection(keyCollectionName);
    }

    public MongoCollection<Document> getPlayerCollection() {
        return playerCollection;
    }
    public MongoCollection<Document> getKeyCollection() {
        return keyCollection;
    }

    public static MongoUtils getInstance() {
        return instance;
    }


    public static void insertOrReplace(Document identifier, Document doc) {
        insertOrReplace(getInstance().getPlayerCollection(), identifier, doc);
    }

    public static void insertOrReplace(MongoCollection<Document> coll, Document identifier, Document doc) {
        if (coll.find(identifier).first() == null) {
            coll.insertOne(doc);
        } else {
            coll.replaceOne(identifier, doc);
        }
    }




}
