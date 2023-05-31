package org.example.ui;


import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import org.example.domain.services.servicesMongo.ServicesMongo;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        SeContainerInitializer initializer = SeContainerInitializer.newInstance();
        final SeContainer container = initializer.initialize();
        ServicesMongo svl = container.select(ServicesMongo.class).get();

        Scanner sc = new Scanner(System.in);
        int option;
        do {
            System.out.println("""
                    \nWhat you want to do?
                    \t1- Convert to mongo
                    \t0- Exit""");
            option = sc.nextInt();
            sc.nextLine();
            switch (option) {
                case 1:

                    break;
                case 0:
                    System.out.println("Bye");
                    break;
                default:
                    System.out.println("Introduce a valid option");
                    break;
            }
        } while (option != 0);
    }
}