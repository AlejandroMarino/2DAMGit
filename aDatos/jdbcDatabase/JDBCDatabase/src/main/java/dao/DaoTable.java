package dao;

import domain.model.Table;
import io.vavr.control.Either;

import java.util.List;

public interface DaoTable {

    Either<Integer, List<Table>> getAll();

    Either<Integer, Table> get(int id);

    Either<Integer, Void> save(Table table);

    Either<Integer, Void> update(Table table);

    Either<Integer, Void> delete(Table table);
}
