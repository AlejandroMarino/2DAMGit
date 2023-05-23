package dao.daoMongo;

import domain.model.modelMongo.Customer;
import domain.model.modelMongo.MenuItem;

import java.util.List;

public interface DaoMongo {

    void save(List<Customer> customers, List<MenuItem> menuItems);
}
