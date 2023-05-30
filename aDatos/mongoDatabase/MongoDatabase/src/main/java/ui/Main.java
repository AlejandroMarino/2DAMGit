package ui;


import domain.model.modelMongo.*;
import io.vavr.control.Either;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import org.bson.Document;
import org.bson.types.ObjectId;
import services.*;

import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        SeContainerInitializer initializer = SeContainerInitializer.newInstance();
        final SeContainer container = initializer.initialize();
        ServicesMongo svl = container.select(ServicesMongo.class).get();
        ServicesCustomer svc = container.select(ServicesCustomer.class).get();
        ServicesMenuItem svmi = container.select(ServicesMenuItem.class).get();
        ServicesOrderItem svoi = container.select(ServicesOrderItem.class).get();
        ServicesOrder svo = container.select(ServicesOrder.class).get();
        ServicesAggregations sva = container.select(ServicesAggregations.class).get();

        Scanner sc = new Scanner(System.in);
        int option;
        do {
            System.out.println("\nWhat you want to do?" +
                    "\n\t1- Convert to mongo" +
                    "\n\t2- CRUD of customer" +
                    "\n\t3- Get order items of order" +
                    "\n\t4- Get the name of the items ordered by a customer" +
                    "\n\t5- Add order with two order-items" +
                    "\n\t6- Show all orders by customer" +
                    "\n\t7- Append a new menu item" +
                    "\n\t8- Delete a menu item" +
                    "\n\t9- Aggregations" +
                    "\n\t0- Exit");
            option = sc.nextInt();
            sc.nextLine();
            switch (option) {
                case 1:
                    convertToMongo(svl);
                    break;
                case 2:
                    crudCustomer(svc, sc);
                    break;
                case 3:
                    getOrderItemsOfOrder(svoi, sc);
                    break;
                case 4:
                    itemsOrderedByCustomer(svmi, sc);
                    break;
                case 5:
                    addOrder(svo, sc);
                    break;
                case 6:
                    getAllCustomers(svc, true);
                    break;
                case 7:
                    addMenuItem(svmi, sc);
                    break;
                case 8:
                    deleteMenuItem(svmi, sc);
                    break;
                case 9:
                    aggregations(sva,sc);
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

    private static void aggregations(ServicesAggregations sva, Scanner sc) {
        char opt;
        do {
            System.out.println("""
                        \nWhat you want to do?
                    a. Get the description of the most expensive item
                    b. Get the orders of a given customer, showing the name of the customer and the
                        number of seats
                    c. Get the number of items of each order
                    d. Get the name of the customers who have ordered steak
                    e. Get the average number of items per order
                    f. Get the item most requested
                    g. Show a list with the number of items of each type requested by a selected customer
                    h. Get the most requested table
                    i. Get the most requested table per customer
                    j. Get the items that have never been requested more than once in an order
                    k. Price paid for each order (Use $lookup)
                    l. Get the name of the customer that has spent more money in the restaurant
                    m. Total not paid amount
                    x. Exit""");
            opt = sc.nextLine().charAt(0);
            switch (opt) {
                case 'a':
                    System.out.println(sva.getMostExpensiveItemDescription());
                    break;
                case 'b':
                    System.out.println("Introduce the id of the customer");
                    String id = sc.nextLine();
                    System.out.println(sva.getOrdersOfCustomer(id));
                    break;
                case 'c':
                    System.out.println(sva.getNumberOfItemsOfOrders());
                    break;
                case 'd':
                    System.out.println(sva.getNameOfCustomerWithSteak());
                    break;
                case 'e':
                    System.out.println(sva.getAverageNumberOfItemsPerOrder());
                    break;
                case 'f':
                    System.out.println(sva.getMostRequestedItem());
                    break;
                case 'g':
                    System.out.println("Introduce the id of the customer");
                    String id2 = sc.nextLine();
                    System.out.println(sva.getNumberOfEachItemOrderedByCustomer(id2));
                    break;
                case 'h':
                    System.out.println(sva.getMostRequestedTable());
                    break;
                case 'i':
                    System.out.println(sva.getMostRequestedTablePerCustomer());
                    break;
                case 'j':
                    System.out.println(sva.getItemsNotRequestedMoreThan1());
                    break;
                case 'k':
                    Either<String, List<Document>> r = sva.getPricePaidForEachOrder();
                    if (r.isLeft()) {
                        System.out.println(r.getLeft());
                    } else {
                        r.get().forEach(System.out::println);
                    }
                    break;
                case 'l':
                    System.out.println(sva.getCustomerSpentMost());
                    break;
                case 'm':
                    System.out.println(sva.getTotalNotPaid());
                    break;
                case 'x':
                    break;
                default:
                    System.out.println("Introduce a valid option");
                    break;
            }
        } while (opt != 'x');
    }

    private static void deleteMenuItem(ServicesMenuItem svmi, Scanner sc) {
        System.out.println("Introduce the id of the item to delete");
        int id = sc.nextInt();
        sc.nextLine();
        Either<Integer, Void> r = svmi.delete(id, false);
        if (r.isLeft()) {
            if (r.getLeft() == -2) {
                System.out.println("There are order items with this menu item, do you want to delete them? (y/n)");
                String answer = sc.nextLine();
                switch (answer) {
                    case "y":
                        Either<Integer, Void> r2 = svmi.delete(id, true);
                        if (r2.isLeft()) {
                            System.out.println("Error deleting menu item");
                        } else {
                            System.out.println("Menu item deleted");
                        }
                        break;
                    case "n":
                        System.out.println("Menu item not deleted");
                        break;
                    default:
                        System.out.println("Invalid option");
                        break;
                }
            } else {
                System.out.println("Error deleting menu item");
            }
        } else {
            System.out.println("Menu item deleted");
        }
    }

    private static void addMenuItem(ServicesMenuItem svmi, Scanner sc) {
        System.out.println("Introduce the name of the item");
        String name = sc.nextLine();
        System.out.println("Introduce the description");
        String description = sc.nextLine();
        System.out.println("Introduce the price");
        double price = sc.nextDouble();
        sc.nextLine();
        Either<String, Void> r = svmi.add(new MenuItem(name, description, price));
        if (r.isLeft()) {
            System.out.println(r.getLeft());
        } else {
            System.out.println("Menu item added");
        }
    }

    private static void addOrder(ServicesOrder svo, Scanner sc) {
        System.out.println("Introduce the id of the customer");
        String idC = sc.nextLine();
        System.out.println("Introduce the number of the table");
        int tN = sc.nextInt();
        sc.nextLine();
        System.out.println("Introduce the number of seats");
        int sN = sc.nextInt();
        sc.nextLine();
        System.out.println("Introduce the name of the menu item");
        String name1 = sc.nextLine();
        System.out.println("Introduce the quantity");
        int q1 = sc.nextInt();
        sc.nextLine();
        System.out.println("Introduce the name of the menu item");
        String name2 = sc.nextLine();
        System.out.println("Introduce the quantity");
        int q2 = sc.nextInt();
        sc.nextLine();
        Table t = new Table(tN, sN);
        OrderItem oi1 = new OrderItem(name1, q1);
        OrderItem oi2 = new OrderItem(name2, q2);
        List<OrderItem> ois = List.of(oi1, oi2);
        Order o = new Order(t, ois);
        Customer c = new Customer(new ObjectId(idC), List.of(o));
        Either<String, Void> r = svo.add(c);
        if (r.isLeft()) {
            System.out.println(r.getLeft());
        } else {
            System.out.println("Order added");
        }
    }

    private static void crudCustomer(ServicesCustomer svc, Scanner sc) {
        System.out.println("What you want to do?" +
                "\n\t1- Get all" +
                "\n\t2- Get" +
                "\n\t3- Add" +
                "\n\t4- Update" +
                "\n\t5- Delete");
        int option2 = sc.nextInt();
        sc.nextLine();
        switch (option2) {
            case 1:
                getAllCustomers(svc, false);
                break;
            case 2:
                getCustomer(svc, sc);
                break;
            case 3:
                addCustomer(svc, sc);
                break;
            case 4:
                updateCustomer(svc, sc);
                break;
            case 5:
                deleteCustomer(svc, sc);
                break;
            default:
                System.out.println("Introduce a valid option");
                break;
        }
    }

    private static void deleteCustomer(ServicesCustomer svc, Scanner sc) {
        System.out.println("Introduce the id of the customer");
        String id = sc.nextLine();
        Either<Integer, Void> r = svc.delete(id, false);
        if (r.isLeft()) {
            if (r.getLeft() == -2) {
                System.out.println("The customer has orders, do you want to delete them? (y/n)");
                String deleteOrders = sc.nextLine();
                switch (deleteOrders) {
                    case "y" -> {
                        Either<Integer, Void> r2 = svc.delete(id, true);
                        if (r2.isLeft()) {
                            System.out.println("Error deleting customer");
                        } else {
                            System.out.println("deleted");
                        }
                    }
                    case "n" -> System.out.println("The customer has not been deleted");
                    default -> System.out.println("Invalid option");
                }
            } else {
                System.out.println("Error deleting customer");
            }
        } else {
            System.out.println("deleted");
        }
    }

    private static void getOrderItemsOfOrder(ServicesOrderItem svoi, Scanner sc) {
        System.out.println("Introduce the date of the order");
        String date = sc.nextLine();
        System.out.println("Introduce the total of the order");
        String total = sc.nextLine();
        System.out.println("Is it paid? (t/f)");
        String paid = sc.nextLine();
        Order o = new Order(date, Double.parseDouble(total), Objects.equals(paid, "t"));
        Either<String, List<OrderItem>> r = svoi.getAllOfOrder(o);
        if (r.isLeft()) {
            System.out.println(r.getLeft());
        } else {
            System.out.println(r.get());
        }
    }

    private static void itemsOrderedByCustomer(ServicesMenuItem svmi, Scanner sc) {
        System.out.println("Introduce the id of the customer");
        String id = sc.nextLine();
        Either<String, List<MenuItem>> r = svmi.getAllOfCustomer(new Customer(new ObjectId(id)));
        if (r.isLeft()) {
            System.out.println(r.getLeft());
        } else {
            r.get().forEach(menuItem -> System.out.println(menuItem.getName()));
        }
    }

    private static void updateCustomer(ServicesCustomer svc, Scanner sc) {
        System.out.println("Introduce the id of the customer");
        String id = sc.nextLine();
        System.out.println("Introduce the new name of the customer");
        String name = sc.nextLine();
        Either<String, Void> r4 = svc.update(new Customer(new ObjectId(id), name));
        if (r4.isLeft()) {
            System.out.println(r4.getLeft());
        } else {
            System.out.println("Updated");
        }
    }

    private static void addCustomer(ServicesCustomer svc, Scanner sc) {
        System.out.println("Introduce the name of the customer");
        String name = sc.nextLine();
        Either<String, Void> r = svc.save(new Customer(name));
        if (r.isLeft()) {
            System.out.println(r.getLeft());
        } else {
            System.out.println("Saved");
        }
    }

    private static void getCustomer(ServicesCustomer svc, Scanner sc) {
        System.out.println("Introduce the id of the customer");
        String id = sc.nextLine();
        Either<String, Customer> r = svc.get(id);
        if (r.isLeft()) {
            System.out.println(r.getLeft());
        } else {
            System.out.println(r.get());
        }
    }

    private static void getAllCustomers(ServicesCustomer svc, boolean withOrders) {
        Either<String, List<Customer>> r = svc.getAll(withOrders);
        if (r.isLeft()) {
            System.out.println(r.getLeft());
        } else {
            if (withOrders) {
                r.get().forEach(customer -> System.out.println(customer.toStringWithOrders()));
            } else {
                System.out.println(r.get());
            }
        }
    }

    private static void convertToMongo(ServicesMongo svl) {
        Either<String, Void> r = svl.saveMongo();
        if (r.isLeft()) {
            System.out.println(r.getLeft());
        } else {
            System.out.println("Saved");
        }
    }

}
