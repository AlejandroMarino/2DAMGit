package ui;

import services.ServicesCustomers;
import services.ServicesLogin;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import services.ServicesMenuItems;
import services.ServicesOrders;
import services.servicesImpl.ServicesXmlImpl;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        SeContainerInitializer initializer = SeContainerInitializer.newInstance();
        final SeContainer container = initializer.initialize();
        ServicesLogin svl = container.select(ServicesLogin.class).get();
        ServicesCustomers sC = container.select(ServicesCustomers.class).get();
        ServicesMenuItems sMI = container.select(ServicesMenuItems.class).get();
        ServicesOrders sO = container.select(ServicesOrders.class).get();
        ServicesXmlImpl sX = container.select(ServicesXmlImpl.class).get();


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
                    System.out.println("Introduce your Username");
                    String user = sc.nextLine().trim();
                    System.out.println("Introduce your Password");
                    String password = sc.nextLine().trim();
                    if (svl.login(user, password)) {
                        System.out.println("Welcome " + user);
                        Menu menu = new Menu();
                        menu.menu(sc, sC, sMI, sO, sX);
                    } else {
                        System.out.println("Wrong credentials");
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
