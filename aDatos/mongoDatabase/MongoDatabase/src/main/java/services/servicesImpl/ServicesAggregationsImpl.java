package services.servicesImpl;

import dao.daoMongo.DaoAggregations;
import domain.model.modelMongo.Customer;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import org.bson.Document;
import org.bson.types.ObjectId;
import services.ServicesAggregations;

import java.util.List;

public class ServicesAggregationsImpl implements ServicesAggregations {

    private final DaoAggregations dA;

    @Inject
    public ServicesAggregationsImpl(DaoAggregations dA) {
        this.dA = dA;
    }


    @Override
    public String getMostExpensiveItemDescription() {
        Either<Integer, String> r = dA.getMostExpensiveItemDescription();
        if (r.isLeft()) {
            return "Error getting most expensive item description";
        } else {
            return r.get();
        }
    }

    @Override
    public String getOrdersOfCustomer(String id) {
        Either<Integer, String> r = dA.getOrdersOfCustomer(new Customer(new ObjectId(id)));
        if (r.isLeft()) {
            return "Error getting orders of customer";
        } else {
            return r.get();
        }
    }

    @Override
    public String getNumberOfItemsOfOrders() {
        Either<Integer, String> r = dA.getNumberOfItemsOfOrders();
        if (r.isLeft()) {
            return "Error getting number of items of orders";
        } else {
            return r.get();
        }
    }

    @Override
    public String getNameOfCustomerWithSteak() {
        Either<Integer, String> r = dA.getNameOfCustomerWithSteak();
        if (r.isLeft()) {
            return "Error getting name of customer with steak";
        } else {
            return r.get();
        }
    }

    @Override
    public String getAverageNumberOfItemsPerOrder() {
        Either<Integer, String> r = dA.getAverageNumberOfItemsPerOrder();
        if (r.isLeft()) {
            return "Error getting average number of items per order";
        } else {
            return r.get();
        }
    }

    @Override
    public String getMostRequestedItem() {
        Either<Integer, String> r = dA.getMostRequestedItem();
        if (r.isLeft()) {
            return "Error getting most requested item";
        } else {
            return r.get();
        }
    }

    @Override
    public String getNumberOfEachItemOrderedByCustomer(String id) {
        Either<Integer, String> r = dA.getNumberOfEachItemOrderedByCustomer(new Customer(new ObjectId(id)));
        if (r.isLeft()) {
            return "Error getting number of each item ordered by customer";
        } else {
            return r.get();
        }
    }

    @Override
    public String getMostRequestedTable() {
        Either<Integer, String> r = dA.getMostRequestedTable();
        if (r.isLeft()) {
            return "Error getting most requested table";
        } else {
            return r.get();
        }
    }

    @Override
    public String getMostRequestedTablePerCustomer() {
        Either<Integer, String> r = dA.getMostRequestedTablePerCustomer();
        if (r.isLeft()) {
            return "Error getting most requested table per customer";
        } else {
            return r.get();
        }
    }

    @Override
    public String getItemsNotRequestedMoreThan1() {
        Either<Integer, String> r = dA.getItemsNotRequestedMoreThan1();
        if (r.isLeft()) {
            return "Error getting items not requested more than 1";
        } else {
            return r.get();
        }
    }

    @Override
    public Either<String, List<Document>> getPricePaidForEachOrder() {
        Either<Integer, List<Document>> r = dA.getPricePaidForEachOrder();
        if (r.isLeft()) {
            return Either.left("Error getting price paid for each order");
        } else {
            return Either.right(r.get());
        }
    }

    @Override
    public String getCustomerSpentMost() {
        Either<Integer, String> r = dA.getCustomerSpentMost();
        if (r.isLeft()) {
            return "Error getting customer spent most";
        } else {
            return r.get();
        }
    }

    @Override
    public String getTotalNotPaid() {
        Either<Integer, String> r = dA.getTotalNotPaid();
        if (r.isLeft()) {
            return "Error getting total not paid";
        } else {
            return r.get();
        }
    }
}
