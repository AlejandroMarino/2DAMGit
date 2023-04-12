package dao.daoImpl;

import config.Configuration;
import dao.DaoOrderItem;
import domain.model.OrderItem;
import io.vavr.control.Either;
import jakarta.inject.Inject;

import java.util.List;

public class DaoOrderItemImpl implements DaoOrderItem {

    private final Configuration config;

    @Inject
    public DaoOrderItemImpl(Configuration config) {
        this.config = config;
    }

    @Override
    public Either<Integer, List<OrderItem>> getAll() {
        return null;
    }

    @Override
    public Either<Integer, OrderItem> get(int id) {
        return null;
    }

    @Override
    public Either<Integer, Void> save(OrderItem orderItem) {
        return null;
    }

    @Override
    public Either<Integer, Void> update(OrderItem orderItem) {
        return null;
    }

    @Override
    public Either<Integer, Void> delete(OrderItem orderItem) {
        return null;
    }
}
