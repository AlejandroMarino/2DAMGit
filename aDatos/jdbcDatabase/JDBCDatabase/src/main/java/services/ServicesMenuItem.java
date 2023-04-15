package services;

import domain.model.MenuItem;
import io.vavr.control.Either;

import java.util.Map;

public interface ServicesMenuItem {
    Either<String, Map<MenuItem, Integer>> getMostOrderedItem();
}
