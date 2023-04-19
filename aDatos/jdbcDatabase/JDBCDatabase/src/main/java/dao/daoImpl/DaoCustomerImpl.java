package dao.daoImpl;

import config.DBConnectionPool;
import dao.DaoCustomer;
import domain.model.Customer;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import lombok.extern.log4j.Log4j2;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Log4j2
public class DaoCustomerImpl implements DaoCustomer {

    private final DBConnectionPool db;

    @Inject
    public DaoCustomerImpl(DBConnectionPool db) {
        this.db = db;
    }

    private List<Customer> readRS(ResultSet rs) {
        List<Customer> customers = new ArrayList<>();
        try {
            while (rs.next()) {
                int customerId = rs.getInt("id");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String email = rs.getString("email");
                String phone = rs.getString("phone");
                customers.add(new Customer(customerId, firstName, lastName, email, phone));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customers;
    }

    @Override
    public Either<Integer, List<Customer>> getAll() {
        List<Customer> customers;
        try (Connection con = db.getConnection();
             Statement statement = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                     ResultSet.CONCUR_READ_ONLY)) {

            ResultSet rs = statement.executeQuery("SELECT * FROM customers");
            customers = readRS(rs);
            return Either.right(customers);
        } catch (SQLException e) {
            Logger.getLogger(DaoCustomer.class.getName()).log(Level.SEVERE, null, e);
            return Either.left(-1);
        }
    }

    @Override
    public Either<Integer, Customer> get(int id) {
        Customer customer;
        try (Connection con = db.getConnection();
             PreparedStatement statement = con.prepareStatement("SELECT * FROM customers WHERE id = ?")) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            List<Customer> customers = readRS(rs);
            if (customers.isEmpty()) {
                return Either.left(-2);
            } else {
                customer = customers.get(0);
                return Either.right(customer);
            }
        } catch (SQLException e) {
            Logger.getLogger(DaoCustomer.class.getName()).log(Level.SEVERE, null, e);
            return Either.left(-1);
        }
    }

    @Override
    public Either<Integer, Void> save(Customer customer) {
        try (Connection con = db.getConnection();
             PreparedStatement statement = con.prepareStatement("INSERT INTO customers (first_name, last_name, email, phone) VALUES (?, ?, ?, ?)")) {
            statement.setString(1, customer.getFirstName());
            statement.setString(2, customer.getLastName());
            statement.setString(3, customer.getEmail());
            statement.setString(4, customer.getPhone());
            statement.executeUpdate();
            return Either.right(null);
        } catch (SQLException e) {
            Logger.getLogger(DaoCustomer.class.getName()).log(Level.SEVERE, null, e);
            return Either.left(-1);
        }
    }

    @Override
    public Either<Integer, Void> update(Customer customer) {
        try (Connection con = db.getConnection();
             PreparedStatement statement = con.prepareStatement("UPDATE customers SET first_name = ?, last_name = ?, email = ?, phone = ? WHERE id = ?")) {
            statement.setString(1, customer.getFirstName());
            statement.setString(2, customer.getLastName());
            statement.setString(3, customer.getEmail());
            statement.setString(4, customer.getPhone());
            statement.setInt(5, customer.getId());
            int rows = statement.executeUpdate();
            if (rows == 0) {
                return Either.left(-2);
            } else {
                return Either.right(null);
            }
        } catch (SQLException e) {
            Logger.getLogger(DaoCustomer.class.getName()).log(Level.SEVERE, null, e);
            return Either.left(-1);
        }
    }

    @Override
    public Either<Integer, Void> delete(Customer customer) {
        boolean error = false;
        Connection con = null;
        try {
            con = db.getConnection();
            con.setAutoCommit(false);
            PreparedStatement statement1 = con.prepareStatement("DELETE FROM order_items WHERE order_id IN (SELECT id FROM orders WHERE customer_id = ?)");
            statement1.setInt(1, customer.getId());
            statement1.executeUpdate();

            PreparedStatement statement2 = con.prepareStatement("DELETE FROM orders WHERE customer_id = ?");
            statement2.setInt(1, customer.getId());
            statement2.executeUpdate();

            PreparedStatement statement3 = con.prepareStatement("DELETE FROM login WHERE customer_id = ?");
            statement3.setInt(1, customer.getId());
            statement3.executeUpdate();

            PreparedStatement statement4 = con.prepareStatement("DELETE FROM customers WHERE id = ?");
            statement4.setInt(1, customer.getId());
            statement4.executeUpdate();

            con.commit();
        } catch (Exception e) {
            try {
                if (con != null) {
                    con.rollback();
                }
                return Either.left(-1);
            } catch (SQLException ex) {
                Logger.getLogger(DaoCustomer.class.getName()).log(Level.SEVERE, null, e);
                return Either.left(-2);
            }
        } finally {
            try {
                if (con != null) {
                    con.setAutoCommit(true);
                    con.close();
                }
            } catch (SQLException e) {
                Logger.getLogger(DaoCustomer.class.getName()).log(Level.SEVERE, null, e);
                error = true;
            }
        }
        if (error) {
            return Either.left(-3);
        }else {
            return Either.right(null);
        }
    }
}