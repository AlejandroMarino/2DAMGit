package ui;


import domain.model.spring.Login;
import io.vavr.control.Either;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import services.*;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        SeContainerInitializer initializer = SeContainerInitializer.newInstance();
        final SeContainer container = initializer.initialize();
        ServicesLogin svl = container.select(ServicesLogin.class).get();
        ServicesOrders sO = container.select(ServicesOrders.class).get();
        ServicesMenuItems sMI = container.select(ServicesMenuItems.class).get();
        ServicesOrderItems sOI = container.select(ServicesOrderItems.class).get();
        ServicesQueries sQ = container.select(ServicesQueries.class).get();
        ServicesXML sXML = container.select(ServicesXML.class).get();

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
                    String username;
                    String password;
                    switch (answer) {
                        case "y":
                            System.out.println("Introduce your username");
                            username = sc.nextLine();
                            System.out.println("Introduce your password");
                            password = sc.nextLine();
                            Either<String, Boolean> result = svl.doLogin(new Login(username, password));
                            if (result.isLeft()) {
                                System.out.println(result.getLeft());
                            } else if (result.get()) {
                                System.out.println("Welcome admin");
                                MenuAdmin menuAdmin = new MenuAdmin();
                                menuAdmin.menu(sc, sO, sOI, sMI, sQ, sXML);
                            } else {
                                System.out.println("Hello " + username);
                                System.out.println("You dont have access");
                            }
                            break;
                        case "n":
                            System.out.println("Introduce your username");
                            username = sc.nextLine();
                            System.out.println("Introduce your password");
                            password = sc.nextLine();
                            svl.register(new Login(username, password));
                            System.out.println("Hello " + username);
                            System.out.println("You dont have access");
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
