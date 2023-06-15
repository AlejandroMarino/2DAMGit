package dao.daoMongo;

import com.google.gson.Gson;
import com.mongodb.client.MongoCollection;
import config.Configuration;
import domain.model.modelMongo.Purchase;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import org.bson.Document;
import org.bson.conversions.Bson;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.push;


public class DaoPurchaseMongo {

    private final Configuration config;
    private final Gson gson;

    @Inject
    public DaoPurchaseMongo(Configuration config, Gson gson) {
        this.config = config;
        this.gson = gson;
    }


    public Either<Integer, Void> update(Purchase purchase) {
        try {
            MongoCollection<Document> purchasesColl = config.getDb().getCollection("examPurchases");
            Document document = Document.parse(gson.toJson(purchase.getItems().get(0)));
            Bson filter = eq("_id", purchase.get_id());
            Bson updateOp = push("items", document);
            long r = purchasesColl.updateOne(filter, updateOp).getModifiedCount();
            if (r >= 1) {
                return Either.right(null);
            } else {
                return Either.left(-2);
            }
        } catch (Exception e) {
            return Either.left(-1);
        }
    }
}
