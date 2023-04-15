package services;

import domain.model.Order;
import io.vavr.control.Either;

import java.util.Map;

public interface ServicesOrder {

    Either<String, Map<Order, Double>> getAllOrdersOfCustomer(int customerId);

    Either<String, Void> addOrder(int customerId, int tableId, String name1, int quantity1, String name2, int quantity2);

    Either<String, Void> editOrder(int customerId, int orderId, int tableId, String name1, int quantity1, String name2, int quantity2);
}
