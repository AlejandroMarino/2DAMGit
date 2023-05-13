package dao;

import io.vavr.control.Either;

import java.util.List;

public interface DaoQueries {

    Either<Integer, List<String>> getOrdersWithNumberOfItems();

    Either<Integer, String> getCustomerSpentMost();
}
