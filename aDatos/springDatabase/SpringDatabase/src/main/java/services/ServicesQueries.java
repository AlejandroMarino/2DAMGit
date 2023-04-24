package services;

import domain.model.spring.ItemQuery;
import domain.model.spring.Table;
import io.vavr.control.Either;

import java.util.List;
import java.util.Map;

public interface ServicesQueries {

    Either<String, Table> getMostRequestedTable();

    Either<String, Map<Table, List<ItemQuery>>> getItemsRequestedTables();
}
