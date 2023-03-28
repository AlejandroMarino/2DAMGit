package ui;

import common.NumericConstants;
import dao.PurchasesDAO;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;

public class Ex2 {

    public static void main(String[] args) {
        SeContainerInitializer initializer = SeContainerInitializer.newInstance();
        final SeContainer container = initializer.initialize();

        PurchasesDAO purchasesDAOImpl = container.select(PurchasesDAO.class).get();

        int code = purchasesDAOImpl.update(2);

        String message  = switch (code){
            case NumericConstants.NON_RELATED_TO_DB_EXCEPTION_CODE -> "ERROR PAYING. Non related to DB";
            case NumericConstants.NOT_FOUND_CODE -> "The client does not exist in the db.";
            case NumericConstants.ALREADY_PAID_CODE -> "The purchase had already been paid. No new changes were made.";
            case NumericConstants.DB_EXCEPTION_CODE -> "ERROR PAYING THE PURCHASE"; // error related to DB
            case NumericConstants.INVALID_VALUES -> "The client does not have enough balance to pay for it";
            case 1 -> "Purchase paid successfully!";
            default -> "UNKNOWN ERROR";
        };

        System.out.println(message);


    }



}
