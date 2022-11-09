package data;

import config.DBConnectionPool;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import modelo.Subscribe;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DaoSubscribe {
    private DBConnectionPool db;

    @Inject
    public DaoSubscribe(DBConnectionPool db) {
        this.db = db;
    }

    private List<Subscribe> subscribeRS(ResultSet rs) {
        List<Subscribe> subscriptions = new ArrayList<>();
        try {
            while (rs.next()) {
                int readerId = rs.getInt("id_reader");
                int newspaperId = rs.getInt("id_newspaper");
                LocalDate startDate = rs.getDate("start_date").toLocalDate();
                LocalDate cancellationDate = rs.getDate("cancellation_date").toLocalDate();
                Subscribe s = new Subscribe(readerId, newspaperId, startDate, cancellationDate);
                subscriptions.add(s);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return subscriptions;
    }

    public Either<Integer, List<Subscribe>> getAll() {
        try (Connection con = db.getConnection();
             Statement statement = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                     ResultSet.CONCUR_READ_ONLY)) {
            ResultSet rs = statement.executeQuery("SELECT * FROM subscribe");

            return Either.right(subscribeRS(rs));
        } catch (SQLException ex) {
            Logger.getLogger(DaoSubscribe.class.getName()).log(Level.SEVERE, null, ex);
            return Either.left(-1);
        }
    }

    public Either<Integer, Subscribe> getSubscribe(int idReader, int idNewspaper) {
        try (Connection con = db.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM subscribe WHERE id_reader = ? AND id_newspaper = ?")) {
            preparedStatement.setInt(1, idReader);
            preparedStatement.setInt(2, idNewspaper);

            ResultSet rs = preparedStatement.executeQuery();
            return Either.right(subscribeRS(rs).get(0));
        } catch (SQLException ex) {
            Logger.getLogger(DaoSubscribe.class.getName()).log(Level.SEVERE, null, ex);
            return Either.left(-1);
        }
    }

    public int add(Subscribe s) {
        try (Connection con = db.getConnection()) {
            try (PreparedStatement preparedStatement = con.prepareStatement("INSERT INTO subscribe (id_reader, id_newspaper, start_date, cancellation_date) VALUES (?, ?, ?, ?)")) {
                con.setAutoCommit(false);
                preparedStatement.setInt(1, s.getIdReader());
                preparedStatement.setInt(2, s.getIdNewspaper());
                preparedStatement.setDate(3, Date.valueOf(s.getStartDate()));
                preparedStatement.setDate(4, Date.valueOf(s.getCancellationDate()));
                con.commit();
                return preparedStatement.executeUpdate();
            } catch (SQLException e) {
                try {
                    con.rollback();
                    return -2;
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    return -3;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(DaoSubscribe.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
    }

    public int update(Subscribe s) {
        if (s.getStartDate() == null) {
            try (Connection con = db.getConnection()) {
                try (PreparedStatement preparedStatement = con.prepareStatement("UPDATE subscribe SET cancellation_date = ? WHERE id_reader = ? AND id_newspaper = ?")) {
                    con.setAutoCommit(false);
                    preparedStatement.setDate(1, Date.valueOf(s.getCancellationDate()));
                    preparedStatement.setInt(2, s.getIdReader());
                    preparedStatement.setInt(3, s.getIdNewspaper());
                    con.commit();
                    return preparedStatement.executeUpdate();
                } catch (SQLException e) {
                    try {
                        con.rollback();
                        return -2;
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        return -3;
                    }
                }
            } catch (SQLException ex) {
                Logger.getLogger(DaoSubscribe.class.getName()).log(Level.SEVERE, null, ex);
                return -1;
            }
        } else if (s.getCancellationDate() == null) {
            try (Connection con = db.getConnection()) {
                try (PreparedStatement preparedStatement = con.prepareStatement("UPDATE subscribe SET start_date = ? WHERE id_reader = ? AND id_newspaper = ?")) {
                    con.setAutoCommit(false);
                    preparedStatement.setDate(1, Date.valueOf(s.getStartDate()));
                    preparedStatement.setInt(2, s.getIdReader());
                    preparedStatement.setInt(3, s.getIdNewspaper());
                    con.commit();
                    return preparedStatement.executeUpdate();
                } catch (SQLException e) {
                    try {
                        con.rollback();
                        return -2;
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        return -3;
                    }
                }
            } catch (SQLException ex) {
                Logger.getLogger(DaoSubscribe.class.getName()).log(Level.SEVERE, null, ex);
                return -1;
            }
        } else {
            try (Connection con = db.getConnection()) {
                try (PreparedStatement preparedStatement = con.prepareStatement("UPDATE subscribe SET start_date = ?, cancellation_date = ? WHERE id_reader = ? AND id_newspaper = ?")) {

                    con.setAutoCommit(false);
                    preparedStatement.setDate(1, Date.valueOf(s.getStartDate()));
                    preparedStatement.setDate(2, Date.valueOf(s.getCancellationDate()));
                    preparedStatement.setInt(3, s.getIdReader());
                    preparedStatement.setInt(4, s.getIdNewspaper());
                    con.commit();
                    return preparedStatement.executeUpdate();
                } catch (SQLException e) {
                    try {
                        con.rollback();
                        return -2;
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        return -3;
                    }
                }
            } catch (SQLException ex) {
                Logger.getLogger(DaoSubscribe.class.getName()).log(Level.SEVERE, null, ex);
                return -1;
            }
        }
    }

    public int delete(int idReader, int idNewspaper) {
        try (Connection con = db.getConnection()) {
            try (PreparedStatement preparedStatement = con.prepareStatement("DELETE FROM subscribe WHERE id_reader = ? AND id_newspaper = ?")) {

                con.setAutoCommit(false);
                preparedStatement.setInt(1, idReader);
                preparedStatement.setInt(2, idNewspaper);
                con.commit();
                return preparedStatement.executeUpdate();
            } catch (SQLException ex) {
                try {
                    con.rollback();
                    return -2;
                } catch (SQLException e) {
                    e.printStackTrace();
                    return -3;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(DaoSubscribe.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
    }
}
