package data;

import config.DBConnectionPool;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import modelo.Newspaper;
import modelo.Reader;
import modelo.Type;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DaoQueries {

    private DBConnectionPool db;
    private DaoReader dR;

    @Inject
    public DaoQueries(DBConnectionPool db, DaoReader daoReader) {
        this.db = db;
        this.dR = daoReader;
    }

    public Either<Integer, List<Reader>> getSuscribedReaders(Newspaper newspaper) {
        try (Connection con = db.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM reader WHERE id IN (SELECT id_reader FROM subscription WHERE id_newspaper = ?)")) {
            preparedStatement.setInt(1, newspaper.getId());

            ResultSet rs = preparedStatement.executeQuery();
            return Either.right(dR.readRS(rs));
        } catch (SQLException ex) {
            Logger.getLogger(DaoReader.class.getName()).log(Level.SEVERE, null, ex);
            return Either.left(-1);
        }
    }

    public Either<Integer,List<Reader>> getArticleTypeReaders(Type type){
        try (Connection con = db.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM reader WHERE id IN (SELECT id_reader FROM article WHERE id_type = ?)")) {
            preparedStatement.setInt(1, type.getId());

            ResultSet rs = preparedStatement.executeQuery();
            return Either.right(dR.readRS(rs));
        } catch (SQLException ex) {
            Logger.getLogger(DaoReader.class.getName()).log(Level.SEVERE, null, ex);
            return Either.left(-1);
        }
    }

    public Either<Integer, List<Reader>> getOldestSubscriptions(Newspaper newspaper) {
        try (Connection con = db.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM reader WHERE id IN (SELECT id_reader FROM subscription WHERE id_newspaper = ? ORDER BY subscription_date ASC LIMIT 5)")) {
            preparedStatement.setInt(1, newspaper.getId());

            ResultSet rs = preparedStatement.executeQuery();
            return Either.right(dR.readRS(rs));
        } catch (SQLException ex) {
            Logger.getLogger(DaoReader.class.getName()).log(Level.SEVERE, null, ex);
            return Either.left(-1);
        }
    }
}
