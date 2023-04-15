package dao.daoImpl;

import config.Configuration;
import config.DBConnectionPool;
import dao.DaoCustomer;
import dao.DaoMenuItem;
import domain.model.Customer;
import domain.model.MenuItem;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import lombok.extern.log4j.Log4j2;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Log4j2
public class DaoMenuItemImpl implements DaoMenuItem {

    private final DBConnectionPool db;

    @Inject
    public DaoMenuItemImpl(DBConnectionPool db) {
        this.db = db;
    }

    private List<MenuItem> readRS(ResultSet rs) {
        List<MenuItem> menuItems = new ArrayList<>();
        try {
            while (rs.next()) {
                int miId = rs.getInt("id");
                String name = rs.getString("name");
                String description = rs.getString("description");
                double price = rs.getDouble("price");
                menuItems.add(new MenuItem(miId, name, description, price));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return menuItems;
    }

    @Override
    public Either<Integer, List<MenuItem>> getAll() {
        List<MenuItem> menuItems;
        try (Connection con = db.getConnection();
             Statement statement = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                     ResultSet.CONCUR_READ_ONLY)) {

            ResultSet rs = statement.executeQuery("SELECT * FROM menu_items");
            menuItems = readRS(rs);
            return Either.right(menuItems);
        } catch (SQLException e) {
            Logger.getLogger(DaoMenuItem.class.getName()).log(Level.SEVERE, null, e);
            return Either.left(-1);
        }
    }

    @Override
    public Either<Integer, MenuItem> get(int id) {
        MenuItem mI;
        try (Connection con = db.getConnection();
             PreparedStatement statement = con.prepareStatement("SELECT * FROM menu_items WHERE id = ?")) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            List<MenuItem> menuItems = readRS(rs);
            if (menuItems.isEmpty()) {
                return Either.left(-2);
            } else {
                mI = menuItems.get(0);
                return Either.right(mI);
            }
        } catch (SQLException e) {
            Logger.getLogger(DaoMenuItem.class.getName()).log(Level.SEVERE, null, e);
            return Either.left(-1);
        }
    }

    @Override
    public Either<Integer, MenuItem> get(String name) {
        MenuItem mI;
        try (Connection con = db.getConnection();
             PreparedStatement statement = con.prepareStatement("SELECT * FROM menu_items WHERE name = ?")) {
            statement.setString(1, name);
            ResultSet rs = statement.executeQuery();
            List<MenuItem> menuItems = readRS(rs);
            if (menuItems.isEmpty()) {
                return Either.left(-2);
            } else {
                mI = menuItems.get(0);
                return Either.right(mI);
            }
        } catch (SQLException e) {
            Logger.getLogger(DaoMenuItem.class.getName()).log(Level.SEVERE, null, e);
            return Either.left(-1);
        }
    }

    @Override
    public Either<Integer, Void> save(MenuItem menuItem) {
        try (Connection con = db.getConnection();
             PreparedStatement statement = con.prepareStatement("INSERT INTO menu_items (name, description, price) VALUES (?, ?, ?)")) {
            statement.setString(1, menuItem.getName());
            statement.setString(2, menuItem.getDescription());
            statement.setDouble(3, menuItem.getPrice());
            statement.executeUpdate();
            return Either.right(null);
        } catch (SQLException e) {
            Logger.getLogger(DaoMenuItem.class.getName()).log(Level.SEVERE, null, e);
            return Either.left(-1);
        }
    }

    @Override
    public Either<Integer, Void> update(MenuItem menuItem) {
        try (Connection con = db.getConnection();
             PreparedStatement statement = con.prepareStatement("UPDATE menu_items SET name = ?, description = ?, price = ? WHERE id = ?")) {
            statement.setString(1, menuItem.getName());
            statement.setString(2, menuItem.getDescription());
            statement.setDouble(3, menuItem.getPrice());
            statement.setInt(4, menuItem.getId());
            statement.executeUpdate();
            return Either.right(null);
        } catch (SQLException e) {
            Logger.getLogger(DaoMenuItem.class.getName()).log(Level.SEVERE, null, e);
            return Either.left(-1);
        }
    }

    @Override
    public Either<Integer, Void> delete(MenuItem menuItem) {
        try (Connection con = db.getConnection();
             PreparedStatement statement = con.prepareStatement("DELETE FROM menu_items WHERE id = ?")) {
            statement.setInt(1, menuItem.getId());
            statement.executeUpdate();
            return Either.right(null);
        } catch (SQLException e) {
            Logger.getLogger(DaoMenuItem.class.getName()).log(Level.SEVERE, null, e);
            return Either.left(-1);
        }
    }
}
