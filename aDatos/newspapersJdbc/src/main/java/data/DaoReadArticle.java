package data;

import config.DBConnectionPool;
import jakarta.inject.Inject;
import modelo.ReadArticle;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DaoReadArticle {
    private DBConnectionPool db;

    @Inject
    public DaoReadArticle(DBConnectionPool db) {
        this.db = db;
    }


    public List<ReadArticle> readArticlesRS(ResultSet rs) {
        List<ReadArticle> readArticles = new ArrayList<>();
        try {
            while (rs.next()) {
                int idArticle = rs.getInt("id_article");
                int idReader = rs.getInt("id_reader");
                int ranking = rs.getInt("ranking");
                ReadArticle ra = new ReadArticle(idArticle, idReader, ranking);
                readArticles.add(ra);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return readArticles;
    }

    public List<ReadArticle> getAll() {
        try (Connection con = db.getConnection();
             Statement statement = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                     ResultSet.CONCUR_READ_ONLY)) {

            ResultSet rs = statement.executeQuery("SELECT * FROM read_article");
            return readArticlesRS(rs);

        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public int add(ReadArticle ra) {
        try (Connection con = db.getConnection()) {
            try (PreparedStatement preparedStatement = con.prepareStatement("INSERT INTO read_article (id_article, id_reader, ranking) VALUES (?, ?, ?)")) {
                con.setAutoCommit(false);
                preparedStatement.setInt(1, ra.getIdArticle());
                preparedStatement.setInt(2, ra.getIdReader());
                preparedStatement.setInt(3, ra.getRanking());
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
            ex.printStackTrace();
            return -1;
        }
    }


    public int delete(int idArticle, int idReader) {
        try (Connection con = db.getConnection()) {
            try (PreparedStatement preparedStatement = con.prepareStatement("DELETE FROM read_article WHERE id_article = ? AND id_reader = ?")) {
                preparedStatement.setInt(1, idArticle);
                preparedStatement.setInt(2, idReader);
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
            ex.printStackTrace();
            return -1;
        }
    }
}
