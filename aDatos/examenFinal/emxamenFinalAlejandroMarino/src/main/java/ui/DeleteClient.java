package ui;

import common.Constants;
import domain.services.servicesHibernate.ServicesClientsHibernate;
import io.vavr.control.Either;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;

import java.util.Scanner;

public class DeleteClient {
    public static void main(String[] args) {
        SeContainerInitializer initializer = SeContainerInitializer.newInstance();
        final SeContainer container = initializer.initialize();
        ServicesClientsHibernate sC = container.select(ServicesClientsHibernate.class).get();

        Scanner sc = new Scanner(System.in);

        System.out.println("Introduce the id of the client you want to delete");
        int id = sc.nextInt();
        sc.nextLine();
        Either<Integer, Void> rDeleteWithOutPurchases = sC.delete(id, false);
        if (rDeleteWithOutPurchases.isRight()) {
            System.out.println("Deleted");
        } else if (rDeleteWithOutPurchases.getLeft() == Constants.HAS_PURCHASES) {
            System.out.println("""
                    This customer has purchases, do you want to delete anyways
                        y- yes
                        n- no
                    """);
            char option = sc.nextLine().trim().toLowerCase().charAt(0);
            switch (option) {
                case 'y':
                    Either<Integer, Void> rdeleteClient = sC.delete(id,true);
                    if (rdeleteClient.isLeft()) {
                        System.out.println("Error while deleting client");
                    } else {
                        System.out.println("client deleted");
                    }
                    break;
                case 'n':
                    System.out.println("The client wasn't deleted");
                    break;
                default:
                    System.out.println("Invalid option, not deleted");
                    break;
            }

        } else {
            System.out.println("Error while deleting");
        }
    }
}
