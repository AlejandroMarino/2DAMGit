package dao.daoImpl;

import common.Queries;
import config.DBConnectionPool;
import dao.DaoOrderItems;
import domain.model.spring.Order;
import domain.model.spring.OrderItem;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DaoOrderItemsImpl implements DaoOrderItems {

    private final DBConnectionPool db;

    @Inject
    public DaoOrderItemsImpl(DBConnectionPool db) {
        this.db = db;
    }


    @Override
    public List<OrderItem> getAll(Order order) {
        JdbcTemplate jtm = new JdbcTemplate(db.getDataSource());
        return jtm.query(Queries.GET_ALL_ORDER_ITEMS_FROM_ORDER,
                BeanPropertyRowMapper.newInstance(OrderItem.class), order.getId());
    }

    @Override
    public Either<Integer, OrderItem> save(OrderItem orderItem) {
        try {
            JdbcTemplate jtm = new JdbcTemplate(db.getDataSource());
            NamedParameterJdbcTemplate namedTemplate = new NamedParameterJdbcTemplate(jtm.getDataSource());

            Map<String, Object> parameters = new HashMap<>();
            parameters.put("order_id", orderItem.getOrderId());
            parameters.put("menu_item_id", orderItem.getMenuItemId());
            parameters.put("quantity", orderItem.getQuantity());

            KeyHolder keyHolder = new GeneratedKeyHolder();
            namedTemplate.update(Queries.ADD_ORDER_ITEM, new MapSqlParameterSource(parameters), keyHolder, new String[]{"id"});

            int id = keyHolder.getKey().intValue();
            orderItem.setId(id);
            return Either.right(orderItem);
        } catch (Exception e) {
            e.printStackTrace();
            return Either.left(-1);
        }
    }

}
