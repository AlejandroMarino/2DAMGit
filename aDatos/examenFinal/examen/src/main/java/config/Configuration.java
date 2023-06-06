package config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import jakarta.inject.Singleton;

@Singleton
public class Configuration {

    MongoClient mongo = MongoClients.create("mongodb://root:root@localhost:27017/");

    MongoDatabase db = mongo.getDatabase("AlejandroMarino");

    public MongoDatabase getDb() {
        return db;
    }
}
