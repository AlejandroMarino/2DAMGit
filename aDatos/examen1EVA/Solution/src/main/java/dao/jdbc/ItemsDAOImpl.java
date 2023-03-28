package dao.jdbc;

import common.NumericConstants;
import dao.ItemsDAO;
import dao.common.SQLQueries;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import model.db.Item;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ItemsDAOImpl implements ItemsDAO {
    private DBConnection dbConnection;

    @Inject
    public ItemsDAOImpl(DBConnection dbConnection){
        this.dbConnection = dbConnection;
    }

    @Override public Either<Integer,Item> get(){
        try (Connection con = dbConnection.getConnection();
             Statement statement = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                     ResultSet.CONCUR_READ_ONLY)) {

            ResultSet rs = statement.executeQuery(SQLQueries.SELECT_TOP_PURCHASED_ITEM);
            if (rs.next()){
                return Either.right(new Item(rs.getString(1)));
            }
            return Either.left(NumericConstants.NOT_FOUND_CODE);

        } catch (SQLException ex) {
            return Either.left(NumericConstants.DB_EXCEPTION_CODE);
        } catch (Exception e){
            return Either.left(NumericConstants.NON_RELATED_TO_DB_EXCEPTION_CODE);
        }

    }




}
