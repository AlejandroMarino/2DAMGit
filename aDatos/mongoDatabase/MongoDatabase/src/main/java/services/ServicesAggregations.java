package services;

import io.vavr.control.Either;
import org.bson.Document;

import java.util.List;

public interface ServicesAggregations {

    String getMostExpensiveItemDescription();

    String getOrdersOfCustomer(String id);

    String getNumberOfItemsOfOrders();

    String getNameOfCustomerWithSteak();

    String getAverageNumberOfItemsPerOrder();

    String getMostRequestedItem();

    String getNumberOfEachItemOrderedByCustomer(String id);

    String getMostRequestedTable();

    String getMostRequestedTablePerCustomer();

    String getItemsNotRequestedMoreThan1();

    Either<String, List<Document>> getPricePaidForEachOrder();

    String getCustomerSpentMost();

    String getTotalNotPaid();
}
