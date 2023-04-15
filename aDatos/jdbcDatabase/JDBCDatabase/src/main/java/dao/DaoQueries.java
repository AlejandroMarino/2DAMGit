package dao;

import domain.model.Customer;
import domain.model.MenuItem;
import io.vavr.control.Either;

import java.util.List;
import java.util.Map;


public interface DaoQueries {


    Either<Integer, List<Customer>> get2CSpentLessMoney();


    Either<Integer, Map<MenuItem, Integer>> getMostOrderedItem();

}
