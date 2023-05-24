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

    private final Gson gson;

    @Inject
    public DaoMongoImpl(Configuration config, Gson gson) {
        this.config = config;
        this.gson = gson;
    }

    @Override
    public void save(List<Customer> customers, List<MenuItem> menuItems) {
        List<Document> customersDoc = new ArrayList<>(emptyList());
        customers.forEach(c -> {
            Document doc = Document.parse(gson.toJson(c));
            customersDoc.add(doc);
        });
        List<Document> menuItemsDoc = new ArrayList<>(emptyList());
        menuItems.forEach(mi -> {
            Document doc = Document.parse(gson.toJson(mi));
            menuItemsDoc.add(doc);
        });
        MongoCollection<Document> restaurantColl = config.getDb().getCollection("restaurant");
        MongoCollection<Document> menuItemColl = config.getDb().getCollection("menuItem");
        restaurantColl.insertMany(customersDoc);
        menuItemColl.insertMany(menuItemsDoc);
    }
}
