package ui;

import common.NumericConstants;
import dao.ClientsDAO;
import dao.jdbc.ClientsDAOImpl;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;

public class Ex4 {
    public static void main(String[] args) {
        SeContainerInitializer initializer = SeContainerInitializer.newInstance();
        final SeContainer container = initializer.initialize();

        ClientsDAO clientsDAO = container.select(ClientsDAOImpl.class).get();
        int idClient = 1;
        int code = clientsDAO.delete(idClient, 0);
        String message;
        if (code != 1) {
            message = switch (code) {
                case NumericConstants.NON_RELATED_TO_DB_EXCEPTION_CODE ->
                        "ERROR deleting client. Non related to DB";
                case NumericConstants.DATA_INTEGRITY_VIOLATION_EXCEPTION -> "The client has purchases. Please confirm the deletion.";
                case NumericConstants.NOT_FOUND_CODE -> "The client with the provided id was not found in the db";
                case NumericConstants.DB_EXCEPTION_CODE -> "ERROR deleting client"; // error related to DB
                default -> "UNKNOWN ERROR";
            };
        } else {
            message = "The client was deleted succesfully!";
        }
        System.out.println(message);
        if (code == NumericConstants.DATA_INTEGRITY_VIOLATION_EXCEPTION){
            code = clientsDAO.delete(idClient, 1);
            if (code == 1){
                System.out.println("Client and purchases deleted!");
            }
        }

    }
}
