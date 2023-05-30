package dao.daoMongo.daoMongoImpl;

import com.google.gson.Gson;
import com.mongodb.client.MongoCollection;
import config.Configuration;
import dao.daoMongo.DaoOrder;
import domain.model.modelMongo.Customer;
import domain.model.modelMongo.Order;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.List;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Projections.include;
import static com.mongodb.client.model.Updates.push;


public class DoaOrderImpl implements DaoOrder {

    private final Configuration config;

    private final Gson gson;

    @Inject
    public DoaOrderImpl(Configuration config, Gson gson) {
        this.config = config;
        this.gson = gson;
    }

    @Override
    public Either<Integer, List<Order>> getAll(Customer customer) {
        try {
            MongoCollection<Document> restaurantColl = config.getDb().getCollection("restaurant");
            Document document = restaurantColl.find(new Document("_id", customer.get_id())).projection(include("orders")).first();
            if (document == null) {
                return Either.left(-2);
            } else {
                if (document.get("orders") == null) {
                    return Either.left(-3);
                } else {
                    List<Order> orders = gson.fromJson(document.toJson(), Customer.class).getOrders();
                    return Either.right(orders);
                }
            }
        } catch (Exception e) {
            return Either.left(-1);
        }
    }

    @Override
    public Either<Integer, Void> save(Customer customer) {
        try {
            MongoCollection<Document> restaurantColl = config.getDb().getCollection("restaurant");
            Document document = Document.parse(gson.toJson(customer.getOrders().get(0)));
            Bson filter = eq("_id", customer.get_id());
            Bson updateOp = push("orders", document);
            long r = restaurantColl.updateOne(filter, updateOp).getModifiedCount();
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
