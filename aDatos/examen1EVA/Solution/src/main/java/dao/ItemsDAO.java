package dao;

import io.vavr.control.Either;
import model.db.Item;

public interface ItemsDAO {
    Either<Integer, Item> get();
}
