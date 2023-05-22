package dao.daoMongo.daoMongoImpl;

import com.mongodb.client.MongoCollection;
import config.Configuration;
import dao.daoMongo.DaoCustomer;
import domain.model.modelMongo.Customer;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import org.bson.Document;

import java.util.List;

public class DaoCustomerImpl implements DaoCustomer {

    private final Configuration config;

    @Inject
    public DaoCustomerImpl(Configuration config) {
        this.config = config;
    }

    @Override
    public Either<Integer, List<Customer>> getAll() {
        MongoCollection<Document> restaurantColl = config.getDb().getCollection("restaurant");

        return null;
    }
}
