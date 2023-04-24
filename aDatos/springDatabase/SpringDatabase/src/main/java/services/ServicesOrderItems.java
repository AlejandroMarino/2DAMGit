package services;

import domain.model.spring.Customer;
import domain.model.spring.MenuItem;
import domain.model.spring.OrderItem;
import io.vavr.control.Either;

public interface ServicesOrderItems {

    Either<String, Void> addOrderItem(MenuItem menuItem, OrderItem orderItem, Customer customer);

}
