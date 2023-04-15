package dao.daoImpl;

import config.DBConnectionPool;
import dao.DaoCustomer;
import dao.DaoOrderItem;
import domain.model.Order;
import domain.model.OrderItem;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import lombok.extern.log4j.Log4j2;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Log4j2
public class DaoOrderItemImpl implements DaoOrderItem {

    private final DBConnectionPool db;

    @Inject
    public DaoOrderItemImpl(DBConnectionPool db) {
        this.db = db;
    }

    private List<OrderItem> readRS(ResultSet rs) {
        List<OrderItem> orderItems = new ArrayList<>();
        try {
            while (rs.next()) {
                int oiId = rs.getInt("id");
                int oId = rs.getInt("order_id");
                int miId = rs.getInt("menu_item_id");
                int quantity = rs.getInt("quantity");
                orderItems.add(new OrderItem(oiId, oId, miId, quantity));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderItems;
    }

    @Override
    public Either<Integer, List<OrderItem>> getAll() {
        List<OrderItem> orderItems;
        try (Connection con = db.getConnection();
             Statement statement = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                     ResultSet.CONCUR_READ_ONLY)) {

            ResultSet rs = statement.executeQuery("SELECT * FROM order_items");
            orderItems = readRS(rs);
            return Either.right(orderItems);
        } catch (SQLException e) {
            Logger.getLogger(DaoOrderItem.class.getName()).log(Level.SEVERE, null, e);
            return Either.left(-1);
        }
    }

    @Override
    public Either<Integer, List<OrderItem>> getAll(int orderId) {
        List<OrderItem> orderItems;
        try (Connection con = db.getConnection();
             PreparedStatement statement = con.prepareStatement("SELECT * FROM order_items WHERE order_id = ?")) {
            statement.setInt(1, orderId);
            ResultSet rs = statement.executeQuery();
            orderItems = readRS(rs);
            return Either.right(orderItems);
        } catch (SQLException e) {
            Logger.getLogger(DaoOrderItem.class.getName()).log(Level.SEVERE, null, e);
            return Either.left(-1);
        }
    }

    @Override
    public Either<Integer, OrderItem> get(int id) {
        OrderItem orderItem;
        try (Connection con = db.getConnection();
             PreparedStatement statement = con.prepareStatement("SELECT * FROM order_items WHERE id = ?")) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            List<OrderItem> orderItems = readRS(rs);
            if (orderItems.isEmpty()) {
                return Either.left(-2);
            } else {
                orderItem = orderItems.get(0);
                return Either.right(orderItem);
            }
        } catch (SQLException e) {
            Logger.getLogger(DaoOrderItem.class.getName()).log(Level.SEVERE, null, e);
            return Either.left(-1);
        }
    }

    @Override
    public Either<Integer, Void> save(OrderItem orderItem) {
        try (Connection con = db.getConnection();
             PreparedStatement statement = con.prepareStatement("INSERT INTO order_items (order_id, menu_item_id, quantity) VALUES (?, ?, ?)")) {
            statement.setInt(1, orderItem.getOrderId());
            statement.setInt(2, orderItem.getMenuItemId());
            statement.setInt(3, orderItem.getQuantity());
            statement.executeUpdate();
            return Either.right(null);
        } catch (SQLException e) {
            Logger.getLogger(DaoOrderItem.class.getName()).log(Level.SEVERE, null, e);
            return Either.left(-1);
        }
    }

    @Override
    public Either<Integer, Void> update(OrderItem orderItem) {
        try (Connection con = db.getConnection();
             PreparedStatement statement = con.prepareStatement("UPDATE order_items SET order_id = ?, menu_item_id = ?, quantity = ? WHERE id = ?")) {
            statement.setInt(1, orderItem.getOrderId());
            statement.setInt(2, orderItem.getMenuItemId());
            statement.setInt(3, orderItem.getQuantity());
            statement.setInt(4, orderItem.getId());
            statement.executeUpdate();
            return Either.right(null);
        } catch (SQLException e) {
            Logger.getLogger(DaoOrderItem.class.getName()).log(Level.SEVERE, null, e);
            return Either.left(-1);
        }
    }

    @Override
    public Either<Integer, Void> delete(OrderItem orderItem) {
        try (Connection con = db.getConnection();
             PreparedStatement statement = con.prepareStatement("DELETE FROM order_items WHERE id = ?")) {
            statement.setInt(1, orderItem.getId());
            statement.executeUpdate();
            return Either.right(null);
        } catch (SQLException e) {
            Logger.getLogger(DaoOrderItem.class.getName()).log(Level.SEVERE, null, e);
            return Either.left(-1);
        }
    }
}
