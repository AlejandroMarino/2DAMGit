package dao.daoImpl;

import config.DBConnectionPool;
import dao.DaoCustomer;
import dao.DaoQueries;
import domain.model.Customer;
import domain.model.MenuItem;
import io.vavr.control.Either;
import jakarta.inject.Inject;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DaoQueriesImpl implements DaoQueries {

    private DBConnectionPool db;

    @Inject
    public DaoQueriesImpl(DBConnectionPool db) {
        this.db = db;
    }

    @Override
    public Either<Integer, List<Customer>> get2CSpentLessMoney() {
        List<Customer> customers;
        try(Connection con = db.getConnection();
            Statement statement = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                     ResultSet.CONCUR_READ_ONLY)) {

            ResultSet rs = statement.executeQuery("SELECT c.id, c.first_name, c.last_name, c.email, c.phone, " +
                    "SUM(mi.price * oi.quantity) AS total_spent " +
                    "FROM customers c " +
                    "LEFT JOIN orders o ON c.id = o.customer_id " +
                    "LEFT JOIN order_items oi ON o.id = oi.order_id " +
                    "LEFT JOIN menu_items mi ON oi.menu_item_id = mi.id " +
                    "WHERE c.id != -1 " +
                    "GROUP BY c.id " +
                    "ORDER BY total_spent " +
                    "LIMIT 2");
            customers = readRSCustomer(rs);
            return Either.right(customers);
        } catch (SQLException e) {
            Logger.getLogger(DaoQueries.class.getName()).log(Level.SEVERE, null, e);
            return Either.left(-1);
        }
    }

    @Override
    public Either<Integer, Map<MenuItem, Integer>> getMostOrderedItem() {
        Map<MenuItem, Integer> result;
        try(Connection con = db.getConnection();
            Statement statement = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                     ResultSet.CONCUR_READ_ONLY)) {

            ResultSet rs = statement.executeQuery("SELECT mi.id, mi.name, mi.description, mi.price, SUM(oi.quantity) AS total_quantity " +
                    "FROM menu_items mi " +
                    "INNER JOIN order_items oi ON mi.id = oi.menu_item_id " +
                    "GROUP BY mi.id " +
                    "ORDER BY total_quantity DESC " +
                    "LIMIT 1");
            result = readRSMenuItem(rs);
            if (result.isEmpty()) {
                return Either.left(-2);
            }else {
                return Either.right(result);
            }
        } catch (SQLException e) {
            Logger.getLogger(DaoQueries.class.getName()).log(Level.SEVERE, null, e);
            return Either.left(-1);
        }
    }

    private Map<MenuItem, Integer> readRSMenuItem(ResultSet rs) {
        Map<MenuItem, Integer> menuItem = new HashMap<>();
        try {
            while (rs.next()) {
                int miId = rs.getInt("id");
                String name = rs.getString("name");
                String description = rs.getString("description");
                double price = rs.getDouble("price");
                int totalQuantity = rs.getInt("total_quantity");
                menuItem.put(new MenuItem(miId, name, description, price), totalQuantity);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return menuItem;
    }

    private List<Customer> readRSCustomer(ResultSet rs) {
        List<Customer> customers = new ArrayList<>();
        try {
            while (rs.next()) {
                int id = rs.getInt("id");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String email = rs.getString("email");
                String phone = rs.getString("phone");
                customers.add(new Customer(id, firstName, lastName, email, phone));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customers;
    }
}
