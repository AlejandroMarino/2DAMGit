package dao.daoImpl;

import config.Configuration;
import dao.DaoCustomers;
import domain.model.Customer;
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
public class DaoCustomersImpl implements DaoCustomers {

    private final Configuration config;

    @Inject
    public DaoCustomersImpl(Configuration config) {
        this.config = config;
    }

    @Override
    public Either<Integer, Customer> get(int id) {
        Customer customer;
        BufferedReader reader;
        Path p = Paths.get(config.getCustomers());
        try {
            reader = Files.newBufferedReader(p, StandardCharsets.UTF_8);
            customer = reader.lines()
                    .filter(line -> {
                        String[] parts = line.split(";");
                        return Integer.parseInt(parts[0]) == id;
                    })
                    .map(Customer::new)
                    .findFirst()
                    .orElse(null);
            reader.close();
            return Either.right(customer);
        } catch (IOException e) {
            log.error(e.getMessage());
            return Either.left(-1);
        }
    }

    @Override
    public Either<Integer, List<Customer>> getAll() {
        List<Customer> customers = new ArrayList<>();
        BufferedReader reader;
        Path p = Paths.get(config.getCustomers());
        try {
            reader = Files.newBufferedReader(p, StandardCharsets.UTF_8);
            reader.lines()
                    .forEach(line -> customers.add(new Customer(line)));
            reader.close();
            return Either.right(customers);
        } catch (IOException e) {
            log.error(e.getMessage());
            return Either.left(-1);
        }
    }

    @Override
    public Either<Integer, Void> save(Customer customer) {
        BufferedWriter writer;
        Path p = Paths.get(config.getCustomers());
        if (get(customer.getId()).isRight()) {
            return Either.left(-1);
        } else {
            try {
                writer = Files.newBufferedWriter(p, StandardCharsets.UTF_8, StandardOpenOption.APPEND);
                writer.write(customer.toString());
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
    public Either<Integer, Void> update(Customer customer) {
        List<Customer> customers = getAll().get();
        if (!customers.removeIf(c -> c.getId() == customer.getId())) {
            return Either.left(-1);
        } else {
            customers.add(customer);
            BufferedWriter writer;
            Path p = Paths.get(config.getCustomers());
            try {
                writer = Files.newBufferedWriter(p, StandardCharsets.UTF_8);
                for (Customer c : customers) {
                    writer.write(c.toString());
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
    public Either<Integer, Void> delete(Customer c) {
        List<Customer> customers = getAll().get();
        customers.remove(c);
        BufferedWriter writer;
        Path p = Paths.get(config.getCustomers());
        try {
            writer = Files.newBufferedWriter(p, StandardCharsets.UTF_8);
            for (Customer customer : customers) {
                writer.write(customer.toString());
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
