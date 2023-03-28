package dao;

import io.vavr.control.Either;
import model.db.Purchase;
import model.db.Purchases_items;

import java.util.List;

public interface PurchasesDAO {
    //add a purchase with two purchase items
    int add(Purchase purchase);

    int update(int idPurchase);

    int update(Purchases_items newPurchaseAmount);

    Either<Integer, List<Purchase>> getAll();

    Either<Integer, List<Purchase>> getAll(int ex);
}
