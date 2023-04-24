package dao.daoImpl;

import common.Queries;
import config.DBConnectionPool;
import dao.DaoCustomers;
import domain.model.spring.Customer;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class DaoCustomersImpl implements DaoCustomers {

    private final DBConnectionPool db;

    @Inject
    public DaoCustomersImpl(DBConnectionPool db) {
        this.db = db;
    }


    @Override
    public Either<Integer, Customer> get(int id) {
        JdbcTemplate jtm = new JdbcTemplate(db.getDataSource());
        List<Customer> l = jtm.query(Queries.GET_CUSTOMER,
                BeanPropertyRowMapper.newInstance(Customer.class), id);
        if (l.isEmpty()){
            return Either.left(-1);
        } else {
            return Either.right(l.get(0));
        }
    }
}
