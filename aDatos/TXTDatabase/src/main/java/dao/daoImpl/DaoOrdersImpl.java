package dao.daoImpl;

import config.Configuration;
import dao.DaoOrders;
import domain.model.Customer;
import domain.model.Order;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import lombok.extern.log4j.Log4j2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

@Log4j2
public class DaoOrdersImpl implements DaoOrders {

    private final Configuration config;

    @Inject
    public DaoOrdersImpl(Configuration config) {
        this.config = config;
    }

    @Override
    public Either<Integer, Order> get(int id) {
        Order order;
        BufferedReader reader;
        Path p = Paths.get(config.getOrders());
        try {
            reader = Files.newBufferedReader(p, StandardCharsets.UTF_8);
            order = reader.lines()
                    .filter(line -> {
                        String[] parts = line.split(";");
                        return Integer.parseInt(parts[0]) == id;
                    })
                    .map(Order::new)
                    .findFirst()
                    .orElse(null);
            reader.close();
            if (order == null) {
                return Either.left(-2);
            } else {
                return Either.right(order);
            }
        } catch (IOException e) {
            log.error(e.getMessage());
            return Either.left(-1);
        }
    }

    @Override
    public Either<Integer, List<Order>> getAll() {
        List<Order> orders = new ArrayList<>();
        BufferedReader reader;
        Path p = Paths.get(config.getOrders());
        try {
            reader = Files.newBufferedReader(p, StandardCharsets.UTF_8);
            reader.lines().forEach(line -> orders.add(new Order(line)));
            reader.close();
            return Either.right(orders);
        } catch (IOException e) {
            log.error(e.getMessage());
            return Either.left(-1);
        }
    }

    @Override
    public Either<Integer, Void> save(Order order) {
        BufferedWriter writer;
        Path p = Paths.get(config.getOrders());
        if (get(order.getId()).isRight()) {
            return Either.left(-1);
        } else {
            try {
                writer = Files.newBufferedWriter(p, StandardCharsets.UTF_8, StandardOpenOption.APPEND);
                writer.write(order.toString());
                writer.newLine();
                writer.close();
                return Either.right(null);
            } catch (IOException e) {
                log.error(e.getMessage());
                return Either.left(-1);
            }
        }
    }

    @Override
    public Either<Integer, Void> update(Order order) {
        return null;
    }

    @Override
    public Either<Integer, Void> delete(Order o) {
        List<Order> orders = getAll().get();
        orders.remove(o);
        BufferedWriter writer;
        Path p = Paths.get(config.getOrders());
        try {
            writer = Files.newBufferedWriter(p, StandardCharsets.UTF_8);
            for (Order order : orders) {
                writer.newLine();
                writer.write(order.toString());
            }
            writer.close();
            return Either.right(null);
        } catch (IOException e) {
            e.printStackTrace();
            return Either.left(-1);
        }
    }
}
