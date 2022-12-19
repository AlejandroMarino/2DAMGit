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

    private final DBConnectionPool db;

    @Inject
    public DaoReader(DBConnectionPool db) {
        this.db = db;
    }

    public List<Reader> readRS(ResultSet rs) {
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

    public Either<Integer, List<Reader>> getAll() {
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

    public Either<Integer, Reader> getReader(int id) {
        try (Connection con = db.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM reader WHERE id = ?")) {
            preparedStatement.setInt(1, id);

            ResultSet rs = preparedStatement.executeQuery();
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
        try (Connection con = db.getConnection()) {
            try (PreparedStatement preparedStatement = con.prepareStatement("DELETE FROM reader WHERE id = ?")) {
                con.setAutoCommit(false);
                preparedStatement.setInt(1, id);
                return preparedStatement.executeUpdate();
            } catch (SQLException ex) {
                try {
                    con.rollback();
                    return -2;
                } catch (SQLException ex1) {
                    return -3;
                }
            }
        } catch (SQLException sqle) {
            Logger.getLogger(DaoReader.class.getName()).log(Level.SEVERE, null, sqle);
            return -1;
        }
    }

    public int add(String name, LocalDate birth_d) {
        try (Connection con = db.getConnection()) {
            try (PreparedStatement preparedStatement = con.prepareStatement("INSERT INTO reader (name_reader, birth_date) VALUES (?,?)",
                    Statement.RETURN_GENERATED_KEYS)) {
                con.setAutoCommit(false);
                preparedStatement.setString(1, name);
                preparedStatement.setDate(2, Date.valueOf(birth_d));
                con.commit();
                return preparedStatement.executeUpdate();
            } catch (SQLException ex) {
                try {
                    con.rollback();
                    return -2;
                } catch (SQLException ex1) {
                    return -3;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public boolean update(Reader r) {
        boolean updated = false;
        if (r.getBirthDate() == null) {
            try (Connection con = db.getConnection()) {
                try (PreparedStatement preparedStatement = con.prepareStatement("UPDATE reader SET name_reader=? WHERE id=?")) {
                    con.setAutoCommit(false);
                    preparedStatement.setString(1, r.getName());
                    preparedStatement.setInt(2, r.getId());
                    updated = preparedStatement.executeUpdate() > 0;
                    con.commit();
                } catch (SQLException ex) {
                    try {
                        con.rollback();
                        return false;
                    } catch (SQLException ex1) {
                        return false;
                    }
                }
            } catch (SQLException sqle) {
                Logger.getLogger(DaoReader.class.getName()).log(Level.SEVERE, null, sqle);
            }
        } else if (r.getName() == null || r.getName().isBlank()) {
            try (Connection con = db.getConnection()) {
                try (PreparedStatement preparedStatement = con.prepareStatement("UPDATE reader SET birth_date=? WHERE id=?")) {
                    con.setAutoCommit(false);
                    preparedStatement.setDate(1, Date.valueOf(r.getBirthDate()));
                    preparedStatement.setInt(2, r.getId());
                    updated = preparedStatement.executeUpdate() > 0;
                } catch (SQLException ex) {
                    try {
                        con.rollback();
                        return false;
                    } catch (SQLException ex1) {
                        return false;
                    }
                }
            } catch (SQLException sqle) {
                Logger.getLogger(DaoReader.class.getName()).log(Level.SEVERE, null, sqle);
            }
        } else {
            try (Connection con = db.getConnection()) {
                try (PreparedStatement preparedStatement = con.prepareStatement("UPDATE reader SET name_reader=?, birth_date=? WHERE id=?")) {
                    con.setAutoCommit(false);
                    preparedStatement.setString(1, r.getName());
                    preparedStatement.setDate(2, Date.valueOf(r.getBirthDate()));
                    preparedStatement.setInt(3, r.getId());
                    con.commit();
                    updated = preparedStatement.executeUpdate() > 0;
                }catch (SQLException ex) {
                    try {
                        con.rollback();
                        return false;
                    } catch (SQLException ex1) {
                        return false;
                    }
                }
            } catch (SQLException sqle) {
                Logger.getLogger(DaoReader.class.getName()).log(Level.SEVERE, null, sqle);
            }
        }
        return updated;
    }
}
