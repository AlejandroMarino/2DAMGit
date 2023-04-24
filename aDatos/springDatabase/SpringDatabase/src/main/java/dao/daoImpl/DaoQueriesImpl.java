package dao.daoImpl;

import common.Queries;
import config.DBConnectionPool;
import dao.DaoQueries;
import domain.model.spring.ItemQuery;
import domain.model.spring.Table;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class DaoQueriesImpl implements DaoQueries {

    private final DBConnectionPool db;

    @Inject
    public DaoQueriesImpl(DBConnectionPool db) {
        this.db = db;
    }

    @Override
    public Either<Integer, Table> getMostRequestedTable() {
        JdbcTemplate jtm = new JdbcTemplate(db.getDataSource());
        List<Table> l = jtm.query(Queries.GET_MOST_REQUESTED_TABLE,
                new RowMapper<Table>() {

                    @Override
                    public Table mapRow(ResultSet rs, int rowNum) throws SQLException {
                        Table table = new Table();
                        table.setId(rs.getInt("id"));
                        table.setTableNumber(rs.getInt("table_number"));
                        table.setNumberOfSeats(rs.getInt("number_of_seats"));
                        return table;
                    }
                });
        if (l.isEmpty()) {
            return Either.left(-1);
        } else {
            return Either.right(l.get(0));
        }
    }

    @Override
    public List<ItemQuery> getItemsRequestedTable(Table table) {
        JdbcTemplate jtm = new JdbcTemplate(db.getDataSource());
        List<ItemQuery> l = jtm.query(Queries.GET_ITEMS_REQUESTED_BY_TABLE,
                new RowMapper<ItemQuery>() {
                    @Override
                    public ItemQuery mapRow(ResultSet rs, int rowNum) throws SQLException {
                        ItemQuery iq = new ItemQuery();
                        iq.setName(rs.getString("name"));
                        iq.setQuantity(rs.getInt("quantity"));
                        return iq;
                    }
                }, table.getId());
        return l;
    }
}
