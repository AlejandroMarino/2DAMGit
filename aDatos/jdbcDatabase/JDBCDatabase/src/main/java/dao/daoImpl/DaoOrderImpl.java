package dao.daoImpl;

import config.Configuration;
import dao.DaoOrder;
import domain.model.Order;
import io.vavr.control.Either;
import jakarta.inject.Inject;

import java.util.List;

public class DaoOrderImpl implements DaoOrder {

    private final Configuration config;

    @Inject
    public DaoOrderImpl(Configuration config) {
        this.config = config;
    }

    @Override
    public Either<Integer, List<Order>> getAll() {
        return null;
    }

    @Override
    public Either<Integer, Order> get(int id) {
        return null;
    }

    @Override
    public Either<Integer, Void> save(Order order) {
        return null;
    }

    @Override
    public Either<Integer, Void> update(Order order) {
        return null;
    }

    @Override
    public Either<Integer, Void> delete(Order order) {
        return null;
    }
}
