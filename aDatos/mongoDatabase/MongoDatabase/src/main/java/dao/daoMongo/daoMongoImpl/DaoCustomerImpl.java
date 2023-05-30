package dao.daoMongo.daoMongoImpl;

import com.google.gson.Gson;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Updates;
import config.Configuration;
import dao.daoMongo.DaoCustomer;
import domain.model.modelMongo.Customer;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import jakarta.persistence.PersistenceException;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Projections.include;
import static com.mongodb.client.model.Updates.set;

public class DaoCustomerImpl implements DaoCustomer {

    private final Configuration config;
    private final Gson gson;

    @Inject
    public DaoCustomerImpl(Configuration config, Gson gson) {
        this.config = config;
        this.gson = gson;
    }

    @Override
    public Either<Integer, List<Customer>> getAll(boolean withOrders) {
        try {
            MongoCollection<Document> restaurantColl = config.getDb().getCollection("restaurant");
            List<Document> documents;
            if (withOrders) {
                documents = restaurantColl.find().into(new ArrayList<>());
            } else {
                documents = restaurantColl.find().projection(include("_id", "name")).into(new ArrayList<>());
            }
            List<Customer> customers = new ArrayList<>();
            for (Document document : documents) {
                ObjectId id = document.getObjectId("_id");
                Customer customer = gson.fromJson(document.toJson(), Customer.class);
                customer.set_id(id);
                customers.add(customer);
            }
            return Either.right(customers);
        } catch (Exception e) {
            return Either.left(-1);
        }
    }

    @Override
    public Either<Integer, Customer> get(String id) {
        try {
            MongoCollection<Document> restaurantColl = config.getDb().getCollection("restaurant");
            Document document = restaurantColl.find(eq("_id", new ObjectId(id))).first();
            if (document == null) {
                return Either.left(-2);
            } else {
                ObjectId idC = document.getObjectId("_id");
                Customer customer = gson.fromJson(document.toJson(), Customer.class);
                customer.set_id(idC);
                return Either.right(customer);
            }
        } catch (Exception e) {
            return Either.left(-1);
        }
    }

    @Override
    public Either<Integer, Void> save(Customer customer) {
        try {
            config.getDb().getCollection("restaurant").insertOne(Document.parse(gson.toJson(customer)));
            return Either.right(null);
        } catch (PersistenceException e) {
            return Either.left(-1);
        } catch (Exception e) {
            return Either.left(-2);
        }
    }

    @Override
    public Either<Integer, Void> update(Customer customer) {
        try {
            Bson filter = eq("_id", customer.get_id());
            Bson update = Updates.combine(set("name", customer.getName()));
            long l = config.getDb().getCollection("restaurant").updateOne(filter, update).getModifiedCount();
            if (l >= 1) {
                return Either.right(null);
            } else {
                return Either.left(-2);
            }
        } catch (Exception e) {
            return Either.left(-1);
        }
    }

    @Override
    public Either<Integer, Void> delete(String id) {
        try {
            config.getDb().getCollection("restaurant").deleteOne(new Document("_id", new ObjectId(id)));
            return Either.right(null);
        } catch (Exception e) {
            return Either.left(-1);
        }
    }
}