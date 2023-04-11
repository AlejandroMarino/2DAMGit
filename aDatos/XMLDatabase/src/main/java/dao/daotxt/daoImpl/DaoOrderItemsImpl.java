package dao.daotxt.daoImpl;

import config.Configuration;
import dao.daotxt.DaoOrderItems;
import domain.model.txt.OrderItem;
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
public class DaoOrderItemsImpl implements DaoOrderItems {

    private final Configuration config;

    @Inject
    public DaoOrderItemsImpl(Configuration config) {
        this.config = config;
    }

    @Override
    public Either<Integer, OrderItem> get(int id) {
        OrderItem orderItem;
        BufferedReader reader;
        Path p = Paths.get(config.getOrderItems());
        try {
            reader = Files.newBufferedReader(p, StandardCharsets.UTF_8);
            orderItem = reader.lines()
                    .filter(line -> {
                        String[] parts = line.split(";");
                        return Integer.parseInt(parts[0]) == id;
                    })
                    .map(OrderItem::new)
                    .findFirst()
                    .orElse(null);
            reader.close();
            if (orderItem == null) {
                return Either.left(-2);
            } else {
                return Either.right(orderItem);
            }
        } catch (IOException e) {
            log.error(e.getMessage());
            return Either.left(-1);
        }
    }

    @Override
    public Either<Integer, List<OrderItem>> getAll() {
        List<OrderItem> orderItems = new ArrayList<>();
        BufferedReader reader;
        Path p = Paths.get(config.getOrderItems());
        try {
            reader = Files.newBufferedReader(p, StandardCharsets.UTF_8);
            reader.lines().forEach(line -> orderItems.add(new OrderItem(line)));
            reader.close();
            return Either.right(orderItems);
        } catch (IOException e) {
            log.error(e.getMessage());
            return Either.left(-1);
        }
    }

    @Override
    public Either<Integer, Void> save(OrderItem orderItem) {
        BufferedWriter writer;
        Path p = Paths.get(config.getOrderItems());
        if (get(orderItem.getId()).isRight()) {
            return Either.left(-1);
        } else {
            try {
                writer = Files.newBufferedWriter(p, StandardCharsets.UTF_8, StandardOpenOption.APPEND);
                writer.write(orderItem.toString());
                writer.newLine();
                writer.close();
                return Either.right(null);
            } catch (IOException e) {
                log.error(e.getMessage());
                return Either.left(-2);
            }
        }
    }

    @Override
    public Either<Integer, Void> update(OrderItem orderItem) {
        List<OrderItem> orderItems = getAll().get();
        if (!orderItems.removeIf(c -> c.getId() == orderItem.getId())) {
            return Either.left(-1);
        } else {
            orderItems.add(orderItem);
            BufferedWriter writer;
            Path p = Paths.get(config.getCustomers());
            try {
                writer = Files.newBufferedWriter(p, StandardCharsets.UTF_8);
                for (OrderItem oi : orderItems) {
                    writer.write(oi.toString());
                    writer.newLine();
                }
                writer.close();
                return Either.right(null);
            } catch (IOException e) {
                log.error(e.getMessage());
                return Either.left(-2);
            }
        }
    }

    @Override
    public Either<Integer, Void> delete(OrderItem o) {
        List<OrderItem> orderItems = getAll().get();
        orderItems.remove(o);
        BufferedWriter writer;
        Path p = Paths.get(config.getOrderItems());
        try {
            writer = Files.newBufferedWriter(p, StandardCharsets.UTF_8);
            for (OrderItem orderItem : orderItems) {
                writer.write(orderItem.toString());
                writer.newLine();
            }
            writer.close();
            return Either.right(null);
        } catch (IOException e) {
            e.printStackTrace();
            return Either.left(-1);
        }
    }
}
