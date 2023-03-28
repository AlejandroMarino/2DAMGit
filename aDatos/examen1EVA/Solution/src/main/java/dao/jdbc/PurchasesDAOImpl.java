package dao.jdbc;

import common.NumericConstants;
import dao.PurchasesDAO;
import dao.common.SQLQueries;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import model.db.Client;
import model.db.Item;
import model.db.Purchase;
import model.db.Purchases_items;
import org.springframework.core.NestedRuntimeException;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PurchasesDAOImpl implements PurchasesDAO {
    private DBConnection dbConnection;

    @Inject
    public PurchasesDAOImpl(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }


    //add a purchase with two purchase items
    @Override
    public int add(Purchase purchase) {
        int code = 1; // <- if no exception is throw, works

         try (Connection con = dbConnection.getConnection()) {

            try (PreparedStatement insertPurchase = con.prepareStatement(SQLQueries.INSERT_INTO_PURCHASES, Statement.RETURN_GENERATED_KEYS);
                 PreparedStatement insertPItem = con.prepareStatement(SQLQueries.INSERT_INTO_PURCHASES_ITEMS);
                 PreparedStatement selectItem = con.prepareStatement(SQLQueries.SELECT_FROM_ITEMS_WHERE_ID);
                 PreparedStatement updateTotalCost = con.prepareStatement(SQLQueries.UPDATE_PURCHASES_SET_TOTALPRICE)) {

                // Disables autocommit
                con.setAutoCommit(false);

                // add la purchase
                insertPurchase.setInt(1, purchase.getClient().getId());
                insertPurchase.setDate(2, Date.valueOf(purchase.getP_date()));
                // the total price at the beginning of the purchase is 0, the query says it

                //we add the purchase to retrieve its id
                insertPurchase.executeUpdate();
                ResultSet rs = insertPurchase.getGeneratedKeys();
                if (rs.next()) {
                    purchase.setId(rs.getInt(1));
                }


                double totalPriceAfter = 0;
                //add purchase_item de la lista y si ok me guardo el precio
                List<Purchases_items> purchases_items = purchase.getItems_purchased();
                if (purchases_items != null && !purchases_items.isEmpty()) {
                    //there are items purchased in this purchase
                    for (Purchases_items p :
                            purchases_items) {
                        //get the price of the item purchased
                        selectItem.setInt(1, p.getItem().getId());
                        double price = 0;
                        ResultSet resultPrice = selectItem.executeQuery();
                        if (resultPrice.next()) {
                            price = resultPrice.getInt(1);
                        }
                        //add a purchase item
                        insertPItem.setInt(1, purchase.getId());
                        insertPItem.setInt(2, p.getItem().getId());
                        insertPItem.setInt(3, p.getAmount());
                        if (insertPItem.executeUpdate() == 1) {
                            //the item was added
                            totalPriceAfter += p.getAmount() * price;
                        }
                    }
                }
                if (totalPriceAfter > 0) {
                    // the total price was updated, we update the purchase item
                    updateTotalCost.setDouble(1, totalPriceAfter);
                    updateTotalCost.setInt(2, purchase.getId());
                    updateTotalCost.executeUpdate();
                }
                con.commit();
            } catch (SQLException ex) {
                con.rollback();
                throw ex; // i do the catch after the rollback
            }
        } catch (SQLException e) {
            // by default is -1
            if (e.getErrorCode() == NumericConstants.NOT_FOUND_EXCEPTION) {
                code = NumericConstants.NOT_FOUND_EXCEPTION;
            } else {
                code = NumericConstants.DB_EXCEPTION_CODE;
            }
        } catch (Exception e) {
            code = NumericConstants.NON_RELATED_TO_DB_EXCEPTION_CODE;
        }
        return code;
    }

    @Override
    public int update(int idPurchase) {

        int code = 1; // <- if no exception is throw, works

        try (Connection con = dbConnection.getConnection()) {

            try (PreparedStatement selectPurchase = con.prepareStatement(SQLQueries.SELECT_THINGS_FROM_PURCHASES);
                 PreparedStatement updBalance = con.prepareStatement(SQLQueries.UPDATE_BALANCE_FROM_CLIENT);
                 PreparedStatement updPurchase = con.prepareStatement(SQLQueries.UPDATE_PAID_FROM_PURCHASES)) {

                // Disables autocommit
                con.setAutoCommit(false);

                // get the purchase price and if paid
                selectPurchase.setInt(1, idPurchase);
                ResultSet rs = selectPurchase.executeQuery();
                if (rs.next()) {
                    int idClient = rs.getInt(1);
                    double total = rs.getDouble(2);
                    int paid = rs.getInt(3);
                    double balance = rs.getDouble(4);
                    if (paid == 0) {
                        if (balance > total) {
                            Purchase pur = new Purchase(total, paid);
                            updBalance.setDouble(1, total);
                            updBalance.setDouble(2, idClient);
                            updBalance.executeUpdate();
                            // update purchase
                            //set the paid
                            updPurchase.setInt(1, 1);
                            updPurchase.setInt(2, idPurchase);
                            code = updPurchase.executeUpdate();
                        } else {
                            code = NumericConstants.INVALID_VALUES;
                        }
                    } else {
                        code = NumericConstants.ALREADY_PAID_CODE;
                    }
            } else{
                code = NumericConstants.NOT_FOUND_CODE;
            }
            con.commit();


        } catch (SQLException ex) {
            con.rollback();
            throw ex; // i do the catch after the rollback
        }
    } catch (SQLException e)

    {
        // by default is -1
        if (e.getErrorCode() == NumericConstants.SQL_INTEGRITY_CONSTRAINT_VIOLATION_EXCEPTION) {
            code = NumericConstants.SQL_INTEGRITY_CONSTRAINT_VIOLATION_EXCEPTION;
        } else {
            code = NumericConstants.DB_EXCEPTION_CODE;
        }
    } catch(
    Exception e)

    {
        code = NumericConstants.NON_RELATED_TO_DB_EXCEPTION_CODE;
    }
        return code;

}


    @Override
    public int update(Purchases_items newPurchaseAmount) { // there is no need to know the client who is purchasing
        int code;
        TransactionDefinition txDef = new DefaultTransactionDefinition();
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(dbConnection.getHikariDataSource());
        TransactionStatus txStatus = transactionManager.getTransaction(txDef);
        try {
            try {
                JdbcTemplate jtm = new JdbcTemplate(Objects.requireNonNull(transactionManager.getDataSource()));
                //get purchase item and id_item,  purchase id, amount, total_cost, price if any
                List<Purchases_items> piList = jtm.query(SQLQueries.SELECT_FROM_PURCHASED_ITEMS, (rs, rowNum) -> {
                    double price = rs.getDouble(1);
                    int id_purchase = rs.getInt(2);
                    int amount = rs.getInt(3);
                    double total_cost = rs.getDouble(4);
                    return new Purchases_items(new Purchase(id_purchase, total_cost), new Item(price), amount);
                }, newPurchaseAmount.getId());

                if (!piList.isEmpty()) {
                    Purchases_items purchases_item = piList.get(0);
                    // set new amount
                    jtm.update(SQLQueries.UPDATE_PURCHASES_ITEMS_SET_AMOUNT, newPurchaseAmount.getAmount(), newPurchaseAmount.getId());

                    // calculate diff to pay
                    double oldPrice = purchases_item.getAmount() * purchases_item.getItem().getPrice();
                    double newPrice = newPurchaseAmount.getAmount() * purchases_item.getItem().getPrice();

                    double newPriceFinal = newPrice - oldPrice;

                    //update total_cost adding diff
                    code = jtm.update(SQLQueries.UPDATE_TOTAL_COST, newPriceFinal, purchases_item.getPurchase().getId());
                } else {
                    // no item with that id
                    code = NumericConstants.NOT_FOUND_CODE;
                }
                // Commit when all movements have been done
                transactionManager.commit(txStatus);
            } catch (NestedRuntimeException e) {
                transactionManager.rollback(txStatus);
                code = NumericConstants.DB_EXCEPTION_CODE;
            } catch (Exception e) {
                transactionManager.rollback(txStatus);
                code = NumericConstants.NON_RELATED_TO_DB_EXCEPTION_CODE;
            }
        } catch (NestedRuntimeException e) {
            code = NumericConstants.DB_EXCEPTION_CODE;
        } catch (Exception e) {
            code = NumericConstants.NON_RELATED_TO_DB_EXCEPTION_CODE;
        }
        return code;
    }


    @Override
    public Either<Integer, List<Purchase>> getAll() {
        try {
            JdbcTemplate jtm = new JdbcTemplate(dbConnection.getHikariDataSource());
            return Either.right(jtm.query(SQLQueries.SELECT_TOTAL_COST_FROM_PURCHASES, (rs, rowNum) -> new Purchase(rs.getDouble(1))));
        } catch (NestedRuntimeException e) {
            return Either.left(NumericConstants.DB_EXCEPTION_CODE);
        } catch (Exception e) {
            return Either.left(NumericConstants.NON_RELATED_TO_DB_EXCEPTION_CODE);
        }
    }

    public Either<Integer, List<Purchase>> getAll(int exercise6) {
        List<Purchase> purchases = new ArrayList<>();
        try {
            String query = SQLQueries.SELECT_PURCHASES;
            JdbcTemplate jdbc = new JdbcTemplate(dbConnection.getHikariDataSource());
            purchases = jdbc.query(query, new RowMapper<Purchase>() {
                @Override
                public Purchase mapRow(ResultSet rs, int rowNum) throws SQLException {

                    Purchase p = new Purchase();
                    p.setId(rs.getInt(1));
                    p.setTotal_cost(rs.getDouble(2));
                    Client c = new Client(rs.getInt(3));
                    p.setClient(c);
                    return p;
                }
            });
        }   catch (DataAccessException e) {
            return Either.left(NumericConstants.DB_EXCEPTION_CODE);
        }
        return purchases.isEmpty() ? Either.left(-1) : Either.right(purchases);
    }
}
