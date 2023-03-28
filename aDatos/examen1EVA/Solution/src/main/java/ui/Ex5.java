package ui;

import common.NumericConstants;
import dao.PurchasesDAO;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import model.db.Purchases_items;

public class Ex5 {
    public static void main(String[] args) {
        SeContainerInitializer initializer = SeContainerInitializer.newInstance();
        final SeContainer container = initializer.initialize();

        PurchasesDAO purchasesDAOImpl = container.select(PurchasesDAO.class).get();
        int code = purchasesDAOImpl.update(new Purchases_items(3, 6));
        System.out.println(code);
        String message;
        if (code != 1) {
            message = switch (code) {
                case NumericConstants.NON_RELATED_TO_DB_EXCEPTION_CODE ->
                        "ERROR updating amount. Non related to DB";
                case NumericConstants.NOT_FOUND_CODE -> "The item purchased with the id provides was not found";
                case NumericConstants.DB_EXCEPTION_CODE -> "ERROR updating amount"; // error related to DB
                default -> "UNKNOWN ERROR";
            };
        } else {
            message = "The amount and the total_cost where updated successfully!";
        }

        System.out.println(message);
    }



}
