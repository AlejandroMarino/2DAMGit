package ui;

import common.NumericConstants;
import dao.PurchasesDAO;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import model.db.Client;
import model.db.Item;
import model.db.Purchase;
import model.db.Purchases_items;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Ex1 {

    public static void main(String[] args) {
        SeContainerInitializer initializer = SeContainerInitializer.newInstance();
        final SeContainer container = initializer.initialize();

        PurchasesDAO purchasesDAOImpl = container.select(PurchasesDAO.class).get();

        //the id, total_cost and paid will be updated, so it is the same
        Purchase purchase = new Purchase(new Client(1), LocalDate.now(), new ArrayList<>());
        List<Purchases_items> items_p = new ArrayList<>();
        items_p.add(new Purchases_items(new Item(1), 3));
        items_p.add(new Purchases_items(new Item(2), 2));

        purchase.setItems_purchased(items_p);


        int code = purchasesDAOImpl.add(purchase);


        String message = switch (code) {
            case NumericConstants.NON_RELATED_TO_DB_EXCEPTION_CODE -> "ERROR PAYING. Non related to DB";
            case NumericConstants.DB_EXCEPTION_CODE -> "ERROR PAYING THE PURCHASE"; // error related to DB
            case NumericConstants.NOT_FOUND_EXCEPTION -> "One or more items do not exist. The purchase was not done.";
            case 1 -> "Purchase items and purchase addes successfully!";
            default -> "UNKNOWN ERROR";
        };

        System.out.println(message);

    }
}
