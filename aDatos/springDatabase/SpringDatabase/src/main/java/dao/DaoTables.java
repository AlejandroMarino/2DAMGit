package dao;

import domain.model.spring.Table;
import io.vavr.control.Either;

import java.util.List;

public interface DaoTables {

    Either<Integer, List<Table>> getAll();

    Either<Integer, Table> get(int id);
}
