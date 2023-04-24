package dao;

import domain.model.spring.ItemQuery;
import domain.model.spring.Table;
import io.vavr.control.Either;

import java.util.List;

public interface DaoQueries {

    Either<Integer, Table> getMostRequestedTable();

    List<ItemQuery> getItemsRequestedTable(Table table);
}
