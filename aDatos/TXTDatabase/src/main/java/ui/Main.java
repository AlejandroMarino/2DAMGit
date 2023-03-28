package ui;

import services.Services;
import services.ServicesLogin;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        SeContainerInitializer initializer = SeContainerInitializer.newInstance();
        final SeContainer container = initializer.initialize();
        ServicesLogin svl = container.select(ServicesLogin.class).get();
        Services sv = container.select(Services.class).get();

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
                        menu.menu(sc, sv);
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
