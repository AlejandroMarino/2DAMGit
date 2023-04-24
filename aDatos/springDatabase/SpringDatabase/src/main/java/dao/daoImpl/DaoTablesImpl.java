package dao.daoImpl;

import common.Queries;
import config.DBConnectionPool;
import dao.DaoTables;
import domain.model.spring.Table;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class DaoTablesImpl implements DaoTables {

    private final DBConnectionPool db;

    @Inject
    public DaoTablesImpl(DBConnectionPool db) {
        this.db = db;
    }


    @Override
    public Either<Integer, List<Table>> getAll() {
        JdbcTemplate jtm = new JdbcTemplate(db.getDataSource());
        List<Table> l = jtm.query(Queries.GET_ALL_TABLES, BeanPropertyRowMapper.newInstance(Table.class));
        if (l.isEmpty()) {
            return Either.left(-1);
        } else {
            return Either.right(l);
        }
    }

    @Override
    public Either<Integer, Table> get(int id) {
        JdbcTemplate jtm = new JdbcTemplate(db.getDataSource());
        List<Table> l = jtm.query(Queries.GET_TABLE,
                BeanPropertyRowMapper.newInstance(Table.class), id);
        if (l.isEmpty()) {
            return Either.left(-1);
        } else {
            return Either.right(l.get(0));
        }
    }
}
