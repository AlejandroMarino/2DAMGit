package dao.daoImpl;

import config.DBConnectionPool;
import dao.DaoOrder;
import domain.model.Order;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import lombok.extern.log4j.Log4j2;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@Log4j2
public class DaoOrderImpl implements DaoOrder {

    private final DBConnectionPool db;

    @Inject
    public DaoOrderImpl(DBConnectionPool db) {
        this.db = db;
    }

    private List<Order> readRS(ResultSet rs) {
        List<Order> orders = new ArrayList<>();
        try {
            while (rs.next()) {
                int oId = rs.getInt("id");
                int tId = rs.getInt("table_id");
                int cId = rs.getInt("customer_id");
                LocalDate date = rs.getDate("order_date").toLocalDate();
                orders.add(new Order(oId, tId, cId, date));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    private Map<Order, Double> readRSTotal(ResultSet rs) {
        Map<Order, Double> orders = new HashMap<>();
        try {
            while (rs.next()) {
                int oId = rs.getInt("id");
                int tId = rs.getInt("table_id");
                int cId = rs.getInt("customer_id");
                LocalDate date = rs.getDate("order_date").toLocalDate();
                double total = rs.getDouble("total");
                orders.put(new Order(oId, tId, cId, date), total);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    @Override
    public Either<Integer, List<Order>> getAll() {
        List<Order> orders;
        try (Connection con = db.getConnection();
             Statement statement = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                     ResultSet.CONCUR_READ_ONLY)) {

            ResultSet rs = statement.executeQuery("SELECT * FROM orders");
            orders = readRS(rs);
            return Either.right(orders);
        } catch (SQLException e) {
            Logger.getLogger(DaoOrder.class.getName()).log(Level.SEVERE, null, e);
            return Either.left(-1);
        }
    }

    @Override
    public Either<Integer, Map<Order, Double>> getAll(int customerId) {
        Map<Order, Double> orders;
        try (Connection con = db.getConnection();
             PreparedStatement statement = con.prepareStatement("SELECT o.id, o.table_id, o.customer_id, o.order_date, " +
                     "SUM(mi.price * oi.quantity) AS total FROM orders o " +
                     "INNER JOIN order_items oi ON o.id = oi.order_id " +
                     "INNER JOIN menu_items mi ON oi.menu_item_id = mi.id " +
                     "where customer_id = ? " +
                     "GROUP BY o.id ")) {
            statement.setInt(1, customerId);
            ResultSet rs = statement.executeQuery();
            orders = readRSTotal(rs);
            return Either.right(orders);
        } catch (SQLException e) {
            Logger.getLogger(DaoOrder.class.getName()).log(Level.SEVERE, null, e);
            return Either.left(-1);
        }
    }

    @Override
    public Either<Integer, Order> get(int id) {
        Order order;
        try (Connection con = db.getConnection();
             PreparedStatement statement = con.prepareStatement("SELECT * FROM orders WHERE id = ?")) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            List<Order> orders = readRS(rs);
            if (orders.isEmpty()) {
                return Either.left(-2);
            } else {
                order = orders.get(0);
                return Either.right(order);
            }
        } catch (SQLException e) {
            Logger.getLogger(DaoOrder.class.getName()).log(Level.SEVERE, null, e);
            return Either.left(-1);
        }
    }

    @Override
    public Either<Integer, Order> save(Order order) {
        try (Connection con = db.getConnection();
             PreparedStatement statement = con.prepareStatement("INSERT INTO orders (table_id, customer_id, order_date) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, order.getTableId());
            statement.setInt(2, order.getCustomerId());
            statement.setDate(3, Date.valueOf(order.getOrderDate()));
            statement.executeUpdate();

            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next()) {
                order.setId(rs.getInt(1));
            }
            return Either.right(order);
        } catch (SQLException e) {
            Logger.getLogger(DaoOrder.class.getName()).log(Level.SEVERE, null, e);
            return Either.left(-1);
        }
    }

    @Override
    public Either<Integer, Void> update(Order order) {
        try (Connection con = db.getConnection();
             PreparedStatement statement = con.prepareStatement("UPDATE orders SET table_id = ?, customer_id = ?, order_date = ? WHERE id = ?")) {
            statement.setInt(1, order.getTableId());
            statement.setInt(2, order.getCustomerId());
            statement.setDate(3, Date.valueOf(order.getOrderDate()));
            statement.setInt(4, order.getId());
            statement.executeUpdate();
            return Either.right(null);
        } catch (SQLException e) {
            Logger.getLogger(DaoOrder.class.getName()).log(Level.SEVERE, null, e);
            return Either.left(-1);
        }
    }

    @Override
    public Either<Integer, Void> delete(Order order) {
        boolean error = false;
        Connection con = null;
        try {
            con = db.getConnection();
            con.setAutoCommit(false);
            PreparedStatement statement1 = con.prepareStatement("DELETE FROM order_items WHERE order_id = ?");
            statement1.setInt(1, order.getId());
            statement1.executeUpdate();

            PreparedStatement statement2 = con.prepareStatement("DELETE FROM orders WHERE id = ?");
            statement2.setInt(1, order.getId());
            statement2.executeUpdate();

            con.commit();
        } catch (Exception e) {
            try {
                if (con != null) {
                    con.rollback();
                }
                return Either.left(-1);
            } catch (SQLException ex) {
                Logger.getLogger(DaoOrder.class.getName()).log(Level.SEVERE, null, e);
                return Either.left(-2);
            }
        } finally {
            try {
                if (con != null) {
                    con.setAutoCommit(true);
                    con.close();
                }
            } catch (SQLException e) {
                Logger.getLogger(DaoOrder.class.getName()).log(Level.SEVERE, null, e);
                error = true;
            }
        }
        if (error) {
            return Either.left(-3);
        } else {
            return Either.right(null);
        }
    }
}
