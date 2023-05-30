package dao.daoMongo;

import domain.model.modelMongo.Customer;
import io.vavr.control.Either;
import org.bson.Document;

import java.util.List;

public interface DaoAggregations {

    Either<Integer, String> getMostExpensiveItemDescription();

    Either<Integer, String> getOrdersOfCustomer(Customer customer);

    Either<Integer, String> getNumberOfItemsOfOrders();

    Either<Integer, String> getNameOfCustomerWithSteak();

    Either<Integer, String> getAverageNumberOfItemsPerOrder();

    Either<Integer, String> getMostRequestedItem();

    Either<Integer, String> getNumberOfEachItemOrderedByCustomer(Customer customer);

    Either<Integer, String> getMostRequestedTable();

    Either<Integer, String> getMostRequestedTablePerCustomer();

    Either<Integer, String> getItemsNotRequestedMoreThan1();

    Either<Integer, List<Document>> getPricePaidForEachOrder();

    Either<Integer, String> getCustomerSpentMost();

    Either<Integer, String> getTotalNotPaid();
}
