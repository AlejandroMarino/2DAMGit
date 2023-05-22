package dao.daoMongo;

import domain.model.modelMongo.Customer;
import domain.model.modelMongo.MenuItem;
import io.vavr.control.Either;

import java.util.List;

public interface DaoMongo {

    void saveMongo(List<Customer> customers, List<MenuItem> menuItems);
}
