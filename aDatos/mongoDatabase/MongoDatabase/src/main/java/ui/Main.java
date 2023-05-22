package ui;


import io.vavr.control.Either;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import services.ServicesMongo;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        SeContainerInitializer initializer = SeContainerInitializer.newInstance();
        final SeContainer container = initializer.initialize();
        ServicesMongo svl = container.select(ServicesMongo.class).get();

        Scanner sc = new Scanner(System.in);
        int option;
        do {
            System.out.println("\nWhat you want to do?" +
                    "\n\t1- Convert to mongo" +
                    "\n\t2- Get information of the order-items of a order" +
                    "\n\t3- Get the name of the items ordered by a customer" +
                    "\n\t4- Add order with two order-items" +
                    "\n\t5- Delete a customer with all the orders" +
                    "\n\t6- Show all orders by customer" +
                    "\n\t7- Append a new menu item" +
                    "\n\t8- Delete a menu item" +
                    "\n\t9- Get the orders of a customer, showing the name of the customer and the number of seats" +
                    "\n\t10- Get the number of items of each order" +
                    "\n\t11- Get the name of the customer that has spent more money" +
                    "\n\t0- Exit");
            option = sc.nextInt();
            sc.nextLine();
            switch (option) {
                case 1:
                    Either<String, Void> r = svl.saveMongo();
                    if (r.isLeft()) {
                        System.out.println(r.getLeft());
                    } else {
                        System.out.println("Saved");
                    }
                    break;
                case 2:

                    break;
                case 3:

                    break;
                case 4:

                    break;
                case 5:

                    break;
                case 6:

                    break;
                case 7:

                    break;
                case 8:

                    break;
                case 9:

                    break;
                case 10:

                    break;
                case 11:

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
