package dao.daoImpl;

import config.Configuration;
import dao.DaoTable;
import domain.model.Table;
import io.vavr.control.Either;
import jakarta.inject.Inject;

import java.util.List;

public class DaoTableImpl implements DaoTable {

    private final Configuration config;

    @Inject
    public DaoTableImpl(Configuration config) {
        this.config = config;
    }

    @Override
    public Either<Integer, List<Table>> getAll() {
        return null;
    }

    @Override
    public Either<Integer, Table> get(int id) {
        return null;
    }

    @Override
    public Either<Integer, Void> save(Table table) {
        return null;
    }

    @Override
    public Either<Integer, Void> update(Table table) {
        return null;
    }

    @Override
    public Either<Integer, Void> delete(Table table) {
        return null;
    }
}
