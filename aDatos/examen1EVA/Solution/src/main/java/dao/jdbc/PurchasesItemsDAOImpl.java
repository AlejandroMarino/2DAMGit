package dao.jdbc;

import common.NumericConstants;
import dao.PurchasesItemsDAO;
import dao.common.SQLQueries;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import model.db.Client;
import model.db.Item;
import model.db.Purchase;
import model.db.Purchases_items;
import org.springframework.core.NestedRuntimeException;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class PurchasesItemsDAOImpl implements PurchasesItemsDAO {
    private DBConnection dbConnection;

    @Inject
    public PurchasesItemsDAOImpl(DBConnection dbConnection){
        this.dbConnection = dbConnection;
    }

    @Override public Either<Integer, List<Purchases_items>> getAll(){
        try {
            JdbcTemplate jtm = new JdbcTemplate(dbConnection.getHikariDataSource());
            return Either.right(jtm.query(SQLQueries.SELECT_ITEM_ID_FROM_PURCHASES_ITEMS, (rs, rowNum) -> new Purchases_items(new Purchase(rs.getInt(1), new Client(rs.getInt(2))), new Item(rs.getInt(3)))));
        } catch (NestedRuntimeException e) {
            return Either.left(NumericConstants.DB_EXCEPTION_CODE);
        } catch (Exception e) {
            return Either.left(NumericConstants.NON_RELATED_TO_DB_EXCEPTION_CODE);
        }
    }
}
