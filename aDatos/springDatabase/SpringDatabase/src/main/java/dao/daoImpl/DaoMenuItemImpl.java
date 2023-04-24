package dao.daoImpl;

import common.Queries;
import config.DBConnectionPool;
import dao.DaoMenuItem;
import domain.model.spring.MenuItem;
import domain.model.spring.OrderItem;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class DaoMenuItemImpl implements DaoMenuItem {

    private final DBConnectionPool db;

    @Inject
    public DaoMenuItemImpl(DBConnectionPool db) {
        this.db = db;
    }

    @Override
    public MenuItem get(OrderItem orderItem) {
        JdbcTemplate jtm = new JdbcTemplate(db.getDataSource());
        List<MenuItem> l = jtm.query(Queries.GET_MENU_ITEM_OF_order_item,
                BeanPropertyRowMapper.newInstance(MenuItem.class), orderItem.getMenuItemId());
        if (l.isEmpty()) {
            return null;
        } else {
            return l.get(0);
        }
    }

    @Override
    public Either<Integer, MenuItem> get(String name) {
        JdbcTemplate jtm = new JdbcTemplate(db.getDataSource());
        List<MenuItem> l = jtm.query(Queries.GET_MENU_ITEM_BY_NAME,
                BeanPropertyRowMapper.newInstance(MenuItem.class), name);
        if (l.isEmpty()) {
            return Either.left(-1);
        } else {
            return Either.right(l.get(0));
        }
    }

    @Override
    public Either<Integer, Void> update(MenuItem menuItem) {
        JdbcTemplate jtm = new JdbcTemplate(db.getDataSource());
        int i = jtm.update(Queries.UPDATE_MENU_ITEM,
                menuItem.getName(), menuItem.getDescription(), menuItem.getPrice(), menuItem.getId());
        if (i == 0) {
            return Either.left(-1);
        } else {
            return Either.right(null);
        }
    }
}
