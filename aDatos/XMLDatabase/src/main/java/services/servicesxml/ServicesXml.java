package services.servicesxml;

import domain.model.xml.Item;
import domain.model.xml.Order;
import io.vavr.control.Either;

import java.util.List;

public interface ServicesXml {

    Either<String, Void> generateXml();

    Either<String, List<Order>> getOrdersOfCustomer(String name);

    Either<String, List<domain.model.xml.Customer>> getCustomersByItem(String name);

    Either<String, Void> addOrder(List<Item> items, String name);

    Either<String, Void> deleteCustomer(String firstName);
}
