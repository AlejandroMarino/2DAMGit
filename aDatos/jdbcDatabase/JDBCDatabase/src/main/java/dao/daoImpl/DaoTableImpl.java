package dao.daoImpl;

import config.Configuration;
import config.DBConnectionPool;
import dao.DaoCustomer;
import dao.DaoTable;
import domain.model.OrderItem;
import domain.model.Table;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import lombok.extern.log4j.Log4j2;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Log4j2
public class DaoTableImpl implements DaoTable {

    private final DBConnectionPool db;

    @Inject
    public DaoTableImpl(DBConnectionPool db) {
        this.db = db;
    }

    private List<Table> readRS(ResultSet rs) {
        List<Table> tables = new ArrayList<>();
        try {
            while (rs.next()) {
                int tId = rs.getInt("id");
                int tN = rs.getInt("table_number");
                int nOS = rs.getInt("number_of_seats");
                tables.add(new Table(tId, tN, nOS));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tables;
    }

    @Override
    public Either<Integer, List<Table>> getAll() {
        List<Table> tables;
        try (Connection con = db.getConnection();
             Statement statement = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                     ResultSet.CONCUR_READ_ONLY)) {

            ResultSet rs = statement.executeQuery("SELECT * FROM tables");
            tables = readRS(rs);
            return Either.right(tables);
        } catch (SQLException e) {
            Logger.getLogger(DaoTable.class.getName()).log(Level.SEVERE, null, e);
            return Either.left(-1);
        }
    }

    @Override
    public Either<Integer, Table> get(int id) {
        Table table;
        try (Connection con = db.getConnection();
             PreparedStatement statement = con.prepareStatement("SELECT * FROM tables WHERE id = ?")) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            List<Table> tables = readRS(rs);
            if (tables.isEmpty()) {
                return Either.left(-2);
            } else {
                table = tables.get(0);
                return Either.right(table);
            }
        } catch (SQLException e) {
            Logger.getLogger(DaoTable.class.getName()).log(Level.SEVERE, null, e);
            return Either.left(-1);
        }
    }

    @Override
    public Either<Integer, Void> save(Table table) {
        try (Connection con = db.getConnection();
             PreparedStatement statement = con.prepareStatement("INSERT INTO tables (table_number, number_of_seats) VALUES (?, ?)")) {
            statement.setInt(1, table.getTableNumber());
            statement.setInt(2, table.getNumberOfSeats());
            statement.executeUpdate();
            return Either.right(null);
        } catch (SQLException e) {
            Logger.getLogger(DaoTable.class.getName()).log(Level.SEVERE, null, e);
            return Either.left(-1);
        }
    }

    @Override
    public Either<Integer, Void> update(Table table) {
        try (Connection con = db.getConnection();
             PreparedStatement statement = con.prepareStatement("UPDATE tables SET table_number = ?, number_of_seats = ? WHERE id = ?")) {
            statement.setInt(1, table.getTableNumber());
            statement.setInt(2, table.getNumberOfSeats());
            statement.setInt(4, table.getId());
            statement.executeUpdate();
            return Either.right(null);
        } catch (SQLException e) {
            Logger.getLogger(DaoTable.class.getName()).log(Level.SEVERE, null, e);
            return Either.left(-1);
        }
    }

    @Override
    public Either<Integer, Void> delete(Table table) {
        boolean error = false;
        Connection con = null;
        try {
            con = db.getConnection();
            con.setAutoCommit(false);
            PreparedStatement statement1 = con.prepareStatement("DELETE FROM order_items WHERE order_id IN (SELECT id FROM orders WHERE table_id = ?)");
            statement1.setInt(1, table.getId());
            statement1.executeUpdate();

            PreparedStatement statement2 = con.prepareStatement("DELETE FROM orders WHERE table_id = ?");
            statement2.setInt(1, table.getId());
            statement2.executeUpdate();

            PreparedStatement statement3 = con.prepareStatement("DELETE FROM tables WHERE id = ?");
            statement3.setInt(1, table.getId());
            statement3.executeUpdate();

            con.commit();
        } catch (Exception e) {
            try {
                if (con != null) {
                    con.rollback();
                }
                return Either.left(-1);
            } catch (SQLException ex) {
                Logger.getLogger(DaoTable.class.getName()).log(Level.SEVERE, null, e);
                return Either.left(-2);
            }
        } finally {
            try {
                if (con != null) {
                    con.setAutoCommit(true);
                    con.close();
                }
            } catch (SQLException e) {
                Logger.getLogger(DaoTable.class.getName()).log(Level.SEVERE, null, e);
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
