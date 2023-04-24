package dao.daoImpl;

import common.Queries;
import config.DBConnectionPool;
import dao.DaoOrders;
import domain.model.spring.Customer;
import domain.model.spring.Order;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DaoOrdersImpl implements DaoOrders {

    private DBConnectionPool db;

    @Inject
    public DaoOrdersImpl(DBConnectionPool db) {
        this.db = db;
    }

    @Override
    public List<Order> getAll() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(db.getDataSource());
        return jdbcTemplate.query(Queries.GET_ALL_ORDERS, BeanPropertyRowMapper.newInstance(Order.class));
    }

    @Override
    public Either<Integer, List<Order>> getAll(boolean paid) {
        JdbcTemplate jtm = new JdbcTemplate(db.getDataSource());
        List<Order> l = jtm.query(Queries.GET_ORDERS_PAID_OLDER_THAN_THIS_YEAR, BeanPropertyRowMapper.newInstance(Order.class), paid);
        if (l.isEmpty()) {
            return Either.left(-1);
        } else {
            return Either.right(l);
        }
    }

    @Override
    public Either<Integer, Order> get(Customer customer) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(db.getDataSource());
        List<Order> l = jdbcTemplate.query(Queries.GET_LAST_ORDER_FROM_CUSTOMER,
                BeanPropertyRowMapper.newInstance(Order.class), customer.getId());
        if (l.isEmpty()) {
            return Either.left(-1);
        } else {
            return Either.right(l.get(0));
        }
    }

    @Override
    public Either<Integer, Void> delete(int id) {
        TransactionDefinition txDef = new DefaultTransactionDefinition();
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(db.getDataSource());
        TransactionStatus txStatus = transactionManager.getTransaction(txDef);
        try {
            JdbcTemplate jtm = new JdbcTemplate(transactionManager.getDataSource());
            jtm.update(Queries.DELETE_ORDER_ITEMS_OF_ORDER, id);
            jtm.update(Queries.DELETE_ORDER, id);
            transactionManager.commit(txStatus);
            return Either.right(null);
        } catch (DataIntegrityViolationException e) {
            if (e.getMessage().contains("IntegrityConstraintViolation")) {
                return Either.left(-2);
            }
            return Either.left(-1);
        } catch (Exception ex) {
            Logger.getLogger(DaoOrders.class.getName()).log(Level.SEVERE, null, ex);
            return Either.left(-1);
        }
    }

}
