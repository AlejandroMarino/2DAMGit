package dao.daoMongo;

import com.google.gson.Gson;
import com.mongodb.client.MongoCollection;
import config.Configuration;
import domain.model.modelMongo.Purchase;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class DaoMongo {

    private final Configuration config;
    private final Gson gson;

    @Inject
    public DaoMongo(Configuration config, Gson gson) {
        this.config = config;
        this.gson = gson;
    }

    public Either<Integer, Void> save(List<Purchase> purchases) {
        try {
            List<Document> documents = new ArrayList<>();
            purchases.forEach(p -> {
                Document doc = Document.parse(gson.toJson(p));
                documents.add(doc);
            });
            MongoCollection<Document> coll = config.getDb().getCollection("examPurchases");
            coll.insertMany(documents);
        } catch (Exception e) {
            e.printStackTrace();
            return Either.left(-1);
        }
        return Either.right(null);
    }
}
