package dao.jdbc;

import common.NumericConstants;
import dao.ClientsDAO;
import dao.common.SQLQueries;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import model.db.Client;
import model.xml.ClientsXML;
import org.springframework.core.NestedRuntimeException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.List;
import java.util.Objects;

public class ClientsDAOImpl implements ClientsDAO {


    private DBConnection dbConnection;

    @Inject
    public ClientsDAOImpl(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }


    @Override
    public Either<Integer, List<Client>> getAll(){
        try {
            JdbcTemplate jtm = new JdbcTemplate(dbConnection.getHikariDataSource());
            return Either.right(jtm.query(SQLQueries.SELECT_ID_NAME_BALANCE_FROM_CLIENTS, BeanPropertyRowMapper.newInstance(Client.class)));
        } catch (NestedRuntimeException e) {
            return Either.left(NumericConstants.DB_EXCEPTION_CODE);
        } catch (Exception e) {
            return Either.left(NumericConstants.NON_RELATED_TO_DB_EXCEPTION_CODE);
        }
    }
    @Override
    public int delete(int id, int confirmed) {
        if (confirmed == 0) {
            // they didnt confirmed yet
            int code = deleteQuery(id);
            if (code == 0) {
                code = NumericConstants.NOT_FOUND_CODE;
            }
            return code;
        } else {
            //they confirmed. everything will be deleted.
            return deleteTransaction(id);
        }
    }

    private int deleteQuery(int id) {
        try {
            JdbcTemplate jtm = new JdbcTemplate(dbConnection.getHikariDataSource());
            return jtm.update(SQLQueries.DELETE_FROM_CLIENTS_BY_ID, id);
        } catch (DataIntegrityViolationException e) {
            return NumericConstants.DATA_INTEGRITY_VIOLATION_EXCEPTION;
        } catch (NestedRuntimeException e) {
            return NumericConstants.DB_EXCEPTION_CODE;
        } catch (Exception e) {
            return NumericConstants.NON_RELATED_TO_DB_EXCEPTION_CODE;
        }
    }

    private int deleteTransaction(int id) {
        int code;
        TransactionDefinition txDef = new DefaultTransactionDefinition();
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(dbConnection.getHikariDataSource());
        TransactionStatus txStatus = transactionManager.getTransaction(txDef);
        try {
            try {
                // clients, purchases, purchases_items
                JdbcTemplate jtm = new JdbcTemplate(Objects.requireNonNull(transactionManager.getDataSource()));
                jtm.update(SQLQueries.DELETE_FROM_PURCHASE_ITEMS_WHERE_ID_CLIENT, id);
                jtm.update(SQLQueries.DELETE_FROM_PURCHASES_WHERE_ID_CLIENT, id);
                code = jtm.update(SQLQueries.DELETE_FROM_CLIENTS_WHERE_ID_CLIENT, id);
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

    public int add(ClientsXML clients){
        return 0;
    }

}
