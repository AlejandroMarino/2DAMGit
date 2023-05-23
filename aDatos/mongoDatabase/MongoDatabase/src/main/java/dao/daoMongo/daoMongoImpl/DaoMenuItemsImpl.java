package dao.daoMongo.daoMongoImpl;

import com.google.gson.Gson;
import com.mongodb.client.MongoCollection;
import config.Configuration;
import dao.daoMongo.DaoMenuItems;
import domain.model.modelMongo.Customer;
import domain.model.modelMongo.MenuItem;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.mongodb.client.model.Filters.elemMatch;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.pull;


public class DaoMenuItemsImpl implements DaoMenuItems {

    private Configuration config;
    private Gson gson;

    @Inject
    public DaoMenuItemsImpl(Configuration config, Gson gson) {
        this.config = config;
        this.gson = gson;
    }


    @Override
    public Either<Integer, List<MenuItem>> getAll(Customer customer) {
        try {
            MongoCollection<Document> restaurantColl = config.getDb().getCollection("restaurant");
            Set<MenuItem> menuItems = new HashSet<>();
            Document document = restaurantColl.find(new Document("_id", customer.get_id()))
                    .projection(new Document("_id", 0).append("orders.orderItems.name", 1)).first();
            if (document == null) {
                return Either.left(-2);
            } else {
                List<Document> documents = (List<Document>) document.get("orders");
                for (Document doc : documents) {
                    List<Document> documents1 = (List<Document>) doc.get("orderItems");
                    for (Document doc1 : documents1) {
                        MenuItem menuItem = gson.fromJson(doc1.toJson(), MenuItem.class);
                        menuItems.add(menuItem);
                    }
                }
            }
            return Either.right(menuItems.stream().toList());
        } catch (Exception e) {
            return Either.left(-1);
        }
    }

    @Override
    public Either<Integer, MenuItem> get(String name) {
        try {
            MongoCollection<Document> mIColl = config.getDb().getCollection("menuItem");
            Document document = mIColl.find(new Document("name", name)).first();
            if (document == null) {
                return Either.left(-2);
            } else {
                MenuItem menuItem = gson.fromJson(document.toJson(), MenuItem.class);
                return Either.right(menuItem);
            }
        } catch (Exception e) {
            return Either.left(-1);
        }
    }

    @Override
    public Either<Integer, Void> save(MenuItem menuItem) {
        try {
            MongoCollection<Document> mIColl = config.getDb().getCollection("menuItem");
            Document document = Document.parse(gson.toJson(menuItem));
            mIColl.insertOne(document);
            return Either.right(null);
        } catch (Exception e) {
            return Either.left(-1);
        }
    }

    @Override
    public Either<Integer, Void> delete(int id, boolean hasOrderItems) {
        try {
            if (hasOrderItems) {
                MongoCollection<Document> rColl = config.getDb().getCollection("restaurant");
                Bson filter = elemMatch("orders.orderItems", eq("menuItemId", id));
                Bson update = pull("orders.$.orderItems", eq("menuItemId", id));
                rColl.updateMany(filter, update);
            }
            MongoCollection<Document> mIColl = config.getDb().getCollection("menuItem");
            mIColl.deleteOne(new Document("_id", id));
            return Either.right(null);
        }catch (Exception e){
            return Either.left(-1);
        }
    }
}
