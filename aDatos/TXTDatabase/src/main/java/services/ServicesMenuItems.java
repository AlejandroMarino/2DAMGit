package services;

import domain.model.MenuItem;
import io.vavr.control.Either;

import java.util.List;

public interface ServicesMenuItems {

    Either<String, List<MenuItem>> getAllMenuItems();
    MenuItem getMenuItem(String name);
}
