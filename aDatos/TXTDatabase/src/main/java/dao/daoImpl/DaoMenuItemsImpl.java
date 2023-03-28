package dao.daoImpl;

import config.Configuration;
import dao.DaoMenuItems;
import domain.model.Customer;
import domain.model.MenuItem;
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
public class DaoMenuItemsImpl implements DaoMenuItems {

    private final Configuration config;

    @Inject
    public DaoMenuItemsImpl(Configuration config) {
        this.config = config;
    }

    @Override
    public Either<Integer, MenuItem> get(int id) {
        MenuItem menuItem;
        BufferedReader reader;
        Path p = Paths.get(config.getMenuItems());
        try {
            reader = Files.newBufferedReader(p, StandardCharsets.UTF_8);
            menuItem = reader.lines()
                    .filter(line -> {
                        String[] parts = line.split(";");
                        return Integer.parseInt(parts[0]) == id;
                    })
                    .map(MenuItem::new)
                    .findFirst()
                    .orElse(null);
            reader.close();
            return Either.right(menuItem);
        } catch (IOException e) {
            log.error(e.getMessage());
            return Either.left(-1);
        }
    }

    @Override
    public Either<Integer, List<MenuItem>> getAll() {
        List<MenuItem> menuItems = new ArrayList<>();
        BufferedReader reader;
        Path p = Paths.get(config.getMenuItems());
        try {
            reader = Files.newBufferedReader(p, StandardCharsets.UTF_8);
            reader.lines().forEach(line -> menuItems.add(new MenuItem(line)));
            reader.close();
            return Either.right(menuItems);
        } catch (IOException e) {
            log.error(e.getMessage());
            return Either.left(-1);
        }
    }

    @Override
    public Either<Integer, Void> save(MenuItem menuItem) {
        BufferedWriter writer;
        Path p = Paths.get(config.getMenuItems());
        if (get(menuItem.getId()).isRight()) {
            return Either.left(-1);
        } else {
            try {
                writer = Files.newBufferedWriter(p, StandardCharsets.UTF_8, StandardOpenOption.APPEND);
                writer.write(menuItem.toString());
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
    public Either<Integer, Void> update(MenuItem menuItem) {
        return null;
    }

    @Override
    public Either<Integer, Void> delete(MenuItem m) {
        List<MenuItem> menuItems = getAll().get();
        menuItems.remove(m);
        BufferedWriter writer;
        Path p = Paths.get(config.getMenuItems());
        try {
            writer = Files.newBufferedWriter(p, StandardCharsets.UTF_8);
            for (MenuItem menuItem : menuItems) {
                writer.newLine();
                writer.write(menuItem.toString());
            }
            writer.close();
            return Either.right(null);
        } catch (IOException e) {
            e.printStackTrace();
            return Either.left(-1);
        }
    }
}
