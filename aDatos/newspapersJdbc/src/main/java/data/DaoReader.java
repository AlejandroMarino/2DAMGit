package data;


import config.DBConnectionPool;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import modelo.Reader;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DaoReader {

    private DBConnectionPool db;

    @Inject
    public DaoReader(DBConnectionPool db) {
        this.db = db;
    }

    private List<Reader> readRS(ResultSet rs) {
        List<Reader> readers = new ArrayList<>();
        try {
            while (rs.next()) {
                int readerId = rs.getInt("id");
                String readerName = rs.getString("name_reader");
                LocalDate date = rs.getDate("birth_date").toLocalDate();
                Reader r = new Reader(readerId, readerName, date);
                readers.add(r);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return readers;
    }

    public Either<Integer ,List<Reader>> getAll(){
        try (Connection con = db.getConnection();
             Statement statement = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                     ResultSet.CONCUR_READ_ONLY)) {

            ResultSet rs = statement.executeQuery("SELECT * FROM reader");
            return Either.right(readRS(rs));

        } catch (SQLException ex) {
            Logger.getLogger(DaoReader.class.getName()).log(Level.SEVERE, null, ex);
            return Either.left(-1);
        }
    }

    public Either<Integer,Reader> getReader(int id){
        try (Connection con = db.getConnection();
             Statement statement = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                     ResultSet.CONCUR_READ_ONLY)) {

            ResultSet rs = statement.executeQuery("SELECT * FROM reader WHERE id = " + id);
            List<Reader> readers = readRS(rs);
            if (readers.isEmpty()) {
                return Either.left(-1);
            } else {
                return Either.right(readers.get(0));
            }

        } catch (SQLException ex) {
            Logger.getLogger(DaoReader.class.getName()).log(Level.SEVERE, null, ex);
            return Either.left(-2);
        }
    }

    public int delete(int id) {
        int rowsAffected=0;
        try (Connection con = db.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement("DELETE FROM reader WHERE id=id")) {
            preparedStatement.setInt(1, id);
            rowsAffected= preparedStatement.executeUpdate();

        } catch (SQLException sqle) {
            Logger.getLogger(DaoReader.class.getName()).log(Level.SEVERE, null, sqle);
        }
        return rowsAffected;
    }

    public int saveWithAutoIncrementalID (String name, LocalDate birth_d){
        int rowsAffected=0;
        try (Connection con = db.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement("INSERT INTO reader (name_reader, birth_date) VALUES (?,?)",
                     Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(2, name);
            preparedStatement.setDate(3, Date.valueOf(birth_d));

            rowsAffected= preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if(rs.next()) {
                int auto_id = rs.getInt(1);
                System.out.println("The id of the new row is "+auto_id);
            }

        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rowsAffected;
    }

    public boolean update(Reader r) {
        boolean updated = false;
        if (r.getBirthDate() == null) {
            try (Connection con = db.getConnection();
                 PreparedStatement preparedStatement = con.prepareStatement("UPDATE reader SET name_reader=? WHERE id=?")) {
                preparedStatement.setString(1, r.getName());
                preparedStatement.setInt(2, r.getId());
                updated = preparedStatement.executeUpdate() > 0;
            } catch (SQLException sqle) {
                Logger.getLogger(DaoReader.class.getName()).log(Level.SEVERE, null, sqle);
            }
        } else if (r.getName() == null || r.getName().isBlank()) {
            try (Connection con = db.getConnection();
                 PreparedStatement preparedStatement = con.prepareStatement("UPDATE reader SET birth_date=? WHERE id=?")) {
                preparedStatement.setDate(1, Date.valueOf(r.getBirthDate()));
                preparedStatement.setInt(2, r.getId());
                updated = preparedStatement.executeUpdate() > 0;
            } catch (SQLException sqle) {
                Logger.getLogger(DaoReader.class.getName()).log(Level.SEVERE, null, sqle);
            }
        }else {
            try (Connection con = db.getConnection();
                 PreparedStatement preparedStatement = con.prepareStatement("UPDATE reader SET name_reader=?, birth_date=? WHERE id=?")) {
                preparedStatement.setString(1, r.getName());
                preparedStatement.setDate(2, Date.valueOf(r.getBirthDate()));
                preparedStatement.setInt(3, r.getId());
                updated = preparedStatement.executeUpdate() > 0;
            } catch (SQLException sqle) {
                Logger.getLogger(DaoReader.class.getName()).log(Level.SEVERE, null, sqle);
            }
        }
        return updated;
    }
}
