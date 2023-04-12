package dao.daoImpl;

import config.Configuration;
import dao.DaoMenuItem;
import domain.model.MenuItem;
import io.vavr.control.Either;
import jakarta.inject.Inject;

import java.util.List;

public class DaoMenuItemImpl implements DaoMenuItem {

    private final Configuration config;

    @Inject
    public DaoMenuItemImpl(Configuration config) {
        this.config = config;
    }

    @Override
    public Either<Integer, List<MenuItem>> getAll() {
        return null;
    }

    @Override
    public Either<Integer, MenuItem> get(int id) {
        return null;
    }

    @Override
    public Either<Integer, Void> save(MenuItem menuItem) {
        return null;
    }

    @Override
    public Either<Integer, Void> update(MenuItem menuItem) {
        return null;
    }

    @Override
    public Either<Integer, Void> delete(MenuItem menuItem) {
        return null;
    }
}
