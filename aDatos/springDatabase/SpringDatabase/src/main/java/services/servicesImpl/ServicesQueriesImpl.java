package services.servicesImpl;

import dao.DaoQueries;
import dao.DaoTables;
import domain.model.spring.ItemQuery;
import domain.model.spring.Table;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import services.ServicesQueries;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServicesQueriesImpl implements ServicesQueries {

    private final DaoQueries dq;
    private final DaoTables dt;

    @Inject
    public ServicesQueriesImpl(DaoQueries dq, DaoTables dt) {
        this.dq = dq;
        this.dt = dt;
    }

    @Override
    public Either<String, Table> getMostRequestedTable() {
        Either<Integer, Table> t = dq.getMostRequestedTable();
        if (t.isLeft()) {
            return Either.left("No tables found");
        } else {
            return Either.right(t.get());
        }
    }

    @Override
    public Either<String, Map<Table, List<ItemQuery>>> getItemsRequestedTables() {
        Map<Table, List<ItemQuery>> map = new HashMap<>();
        Either<Integer, List<Table>> tl = dt.getAll();
        if (tl.isLeft()) {
            return Either.left("No tables found");
        } else {
            tl.get().forEach(table -> {
                List<ItemQuery> il = dq.getItemsRequestedTable(table);
                map.put(table, il);
            });
            return Either.right(map);
        }
    }
}
