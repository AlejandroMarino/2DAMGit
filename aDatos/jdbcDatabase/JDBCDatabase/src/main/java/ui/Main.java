package ui;


import io.vavr.control.Either;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import services.ServicesCustomer;
import services.ServicesLogin;
import services.ServicesMenuItem;
import services.ServicesOrder;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        SeContainerInitializer initializer = SeContainerInitializer.newInstance();
        final SeContainer container = initializer.initialize();
        ServicesLogin svl = container.select(ServicesLogin.class).get();
        ServicesCustomer sC = container.select(ServicesCustomer.class).get();
        ServicesOrder sO = container.select(ServicesOrder.class).get();
        ServicesMenuItem sMI = container.select(ServicesMenuItem.class).get();

        Scanner sc = new Scanner(System.in);
        int opcion;
        do {
            System.out.println("Welcome to the restaurant" +
                    "\n\tPress 1 to login" +
                    "\n\tPress 0 to exit");
            opcion = sc.nextInt();
            sc.nextLine();
            switch (opcion) {
                case 1:
                    System.out.println("Have you registered? (y/n)");
                    String answer = sc.nextLine();
                    int customerId;
                    String password;
                    switch (answer) {
                        case "y":
                            System.out.println("Introduce your customer id");
                            customerId = sc.nextInt();
                            sc.nextLine();
                            System.out.println("Introduce your password");
                            password = sc.nextLine();
                            Either<String, Boolean> result = svl.doLogin(customerId, password);
                            if (result.isLeft()) {
                                System.out.println(result.getLeft());
                            } else if (result.get()){
                                System.out.println("Welcome admin");
                                MenuAdmin menuAdmin = new MenuAdmin();
                                menuAdmin.menu(sc, sC, sO, sMI);
                            } else {
                                System.out.println("Welcome customer");
                                MenuCustomer menuCustomer = new MenuCustomer();
                                menuCustomer.menu(sc, customerId, sO);
                            }
                            break;
                        case "n":
                            System.out.println("Introduce your customer id");
                            customerId = sc.nextInt();
                            sc.nextLine();
                            System.out.println("Introduce your password");
                            password = sc.nextLine();
                            Either<String, Void> r = svl.register(customerId, password);
                            if (r.isLeft()) {
                                System.out.println(r.getLeft());
                            } else {
                                System.out.println("Welcome customer");
                                MenuCustomer menuCustomer = new MenuCustomer();
                                menuCustomer.menu(sc, customerId, sO);
                            }
                            break;
                        default:
                            System.out.println("Invalid option");
                            break;
                    }
                    break;
                case 0:
                    System.out.println("Bye");
                    break;
                default:
                    System.out.println("Introduce a valid option");
                    break;
            }
        } while (opcion != 0);
    }
}
