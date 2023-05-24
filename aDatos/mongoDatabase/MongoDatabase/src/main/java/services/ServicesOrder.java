package services;

import domain.model.modelMongo.Customer;
import domain.model.modelMongo.Order;
import io.vavr.control.Either;

import java.util.List;

public interface ServicesOrder {
    Either<String, List<Order>> getAll(Customer customer);
    Either<String, Void> add(Customer customer);
}
