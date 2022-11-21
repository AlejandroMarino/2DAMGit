package data;


import config.DBConnectionPool;
import data.modelo.Reader;
import domain.modelo.BaseDatosCaidaException;
import domain.modelo.NotFoundException;
import jakarta.inject.Inject;

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

    public List<Reader> getAll() {
        try (Connection con = db.getConnection();
             Statement statement = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                     ResultSet.CONCUR_READ_ONLY)) {

            ResultSet rs = statement.executeQuery("SELECT * FROM reader");
            return readRS(rs);

        } catch (SQLException ex) {
            Logger.getLogger(DaoReader.class.getName()).log(Level.SEVERE, null, ex);
            throw new BaseDatosCaidaException("Error en la base de datos");
        }
    }

    public Reader getReader(int id) {
        try (Connection con = db.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM reader WHERE id = ?")) {
            preparedStatement.setInt(1, id);

            ResultSet rs = preparedStatement.executeQuery();
            List<Reader> readers = readRS(rs);
            if (readers.isEmpty()) {
                throw new NotFoundException("No se ha encontrado el lector");
            } else {
                return readers.get(0);
            }

        } catch (SQLException ex) {
            Logger.getLogger(DaoReader.class.getName()).log(Level.SEVERE, null, ex);
            throw new BaseDatosCaidaException("Error en la base de datos");
        }
    }

    public void delete(int id) {
        try (Connection con = db.getConnection()) {
            try (PreparedStatement preparedStatement = con.prepareStatement("DELETE FROM reader WHERE id = ?")) {
                con.setAutoCommit(false);
                preparedStatement.setInt(1, id);
                preparedStatement.executeUpdate();
            } catch (SQLException ex) {
                try {
                    con.rollback();
                    throw new NotFoundException("No se ha encontrado el lector");
                } catch (SQLException ex1) {
                    throw new BaseDatosCaidaException("Error en la base de datos");
                }
            }
        } catch (SQLException sqle) {
            Logger.getLogger(DaoReader.class.getName()).log(Level.SEVERE, null, sqle);
            throw new BaseDatosCaidaException("Error en la base de datos");
        }
    }

    public Reader add(Reader reader) {
        List<Reader> readers;
        try (Connection con = db.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement("INSERT INTO reader (name_reader, birth_date) VALUES (?,?)",
                     Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, reader.getName());
            preparedStatement.setDate(2, Date.valueOf(reader.getBirthDate()));
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            readers = readRS(rs);
        } catch (Exception e) {
            throw new BaseDatosCaidaException("Error al conectar con base de datos");
        }
        return readers.get(0);
    }

    public Reader update(Reader r) {
        if (r.getBirthDate() == null) {
            try (Connection con = db.getConnection()) {
                try (PreparedStatement preparedStatement = con.prepareStatement("UPDATE reader SET name_reader=? WHERE id=?")) {
                    con.setAutoCommit(false);
                    preparedStatement.setString(1, r.getName());
                    preparedStatement.setInt(2, r.getId());
                    con.commit();
                    preparedStatement.executeUpdate();
                } catch (SQLException ex) {
                    try {
                        con.rollback();
                        throw new NotFoundException("No se ha podido actualizar el lector");
                    } catch (SQLException ex1) {
                        throw new BaseDatosCaidaException("Error en la base de datos");
                    }
                }
            } catch (SQLException sqle) {
                Logger.getLogger(DaoReader.class.getName()).log(Level.SEVERE, null, sqle);
                throw new BaseDatosCaidaException("Error en la base de datos");
            }
        } else if (r.getName() == null || r.getName().isBlank()) {
            try (Connection con = db.getConnection()) {
                try (PreparedStatement preparedStatement = con.prepareStatement("UPDATE reader SET birth_date=? WHERE id=?")) {
                    con.setAutoCommit(false);
                    preparedStatement.setDate(1, Date.valueOf(r.getBirthDate()));
                    preparedStatement.setInt(2, r.getId());
                    con.commit();
                    preparedStatement.executeUpdate();
                } catch (SQLException ex) {
                    try {
                        con.rollback();
                        throw new NotFoundException("No se ha podido actualizar el lector");
                    } catch (SQLException ex1) {
                        throw new BaseDatosCaidaException("Error en la base de datos");
                    }
                }
            } catch (SQLException sqle) {
                Logger.getLogger(DaoReader.class.getName()).log(Level.SEVERE, null, sqle);
                throw new BaseDatosCaidaException("Error en la base de datos");
            }
        } else {
            try (Connection con = db.getConnection()) {
                try (PreparedStatement preparedStatement = con.prepareStatement("UPDATE reader SET name_reader=?, birth_date=? WHERE id=?")) {
                    con.setAutoCommit(false);
                    preparedStatement.setString(1, r.getName());
                    preparedStatement.setDate(2, Date.valueOf(r.getBirthDate()));
                    preparedStatement.setInt(3, r.getId());
                    con.commit();
                    preparedStatement.executeUpdate();
                } catch (SQLException ex) {
                    try {
                        con.rollback();
                        throw new NotFoundException("No se ha podido actualizar el lector");
                    } catch (SQLException ex1) {
                        throw new BaseDatosCaidaException("Error en la base de datos");
                    }
                }
            } catch (SQLException sqle) {
                Logger.getLogger(DaoReader.class.getName()).log(Level.SEVERE, null, sqle);
                throw new BaseDatosCaidaException("Error en la base de datos");
            }
        }
        return r;
    }
}
