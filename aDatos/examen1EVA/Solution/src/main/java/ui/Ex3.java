package ui;

import common.NumericConstants;
import dao.ItemsDAO;
import io.vavr.control.Either;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import model.db.Item;

public class Ex3 {
    public static void main(String[] args) {
        SeContainerInitializer initializer = SeContainerInitializer.newInstance();
        final SeContainer container = initializer.initialize();

        ItemsDAO itemsDAOImpl = container.select(ItemsDAO.class).get();

        Either<Integer, Item> result = itemsDAOImpl.get();
        String message;
        if (result.isLeft()) {
             message = switch (result.getLeft()) {
                case NumericConstants.NON_RELATED_TO_DB_EXCEPTION_CODE ->
                        "ERROR getting top purchased item. Non related to DB";
                case NumericConstants.NOT_FOUND_CODE -> "There are no items in the db";
                case NumericConstants.DB_EXCEPTION_CODE -> "ERROR getting top purchased item"; // error related to DB
                default -> "UNKNOWN ERROR";
            };
        } else {
            message = "The name of the most purchased item is: " + result.get().getName();
        }
        System.out.println(message);


    }
}
