package dao.daoMongo.daoMongoImpl;

import com.google.gson.Gson;
import com.mongodb.client.MongoCollection;
import config.Configuration;
import dao.daoMongo.DaoMongo;
import domain.model.modelMongo.Customer;
import domain.model.modelMongo.MenuItem;
import jakarta.inject.Inject;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.emptyList;

public class DaoMongoImpl implements DaoMongo {

    private final Configuration config;

    @Inject
    public DaoMongoImpl(Configuration config) {
        this.config = config;
    }

    @Override
    public void saveMongo(List<Customer> customers, List<MenuItem> menuItems) {
        List<Document> customersDoc = new ArrayList<>(emptyList());
        customers.forEach(c -> {
            Document doc = Document.parse(new Gson().toJson(c));
            customersDoc.add(doc);
        });
        List<Document> menuItemsDoc = new ArrayList<>(emptyList());
        menuItems.forEach(mi -> {
            Document doc = Document.parse(new Gson().toJson(mi));
            menuItemsDoc.add(doc);
        });
        MongoCollection<Document> restaurantColl = config.getDb().getCollection("restaurant");
        MongoCollection<Document> menuItemColl = config.getDb().getCollection("menuItem");
        restaurantColl.insertMany(customersDoc);
        menuItemColl.insertMany(menuItemsDoc);

    }
}
