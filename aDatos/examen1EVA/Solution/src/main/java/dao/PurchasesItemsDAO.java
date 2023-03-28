package dao;

import io.vavr.control.Either;
import model.db.Purchases_items;

import java.util.List;

public interface PurchasesItemsDAO {
    Either<Integer, List<Purchases_items>> getAll();
}
