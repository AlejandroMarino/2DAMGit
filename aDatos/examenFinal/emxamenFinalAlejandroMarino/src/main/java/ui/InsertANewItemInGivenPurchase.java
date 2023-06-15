package ui;

import domain.model.modelMongo.Item;
import domain.model.modelMongo.Purchase;
import domain.services.servicesMongo.ServicesPurchaseMongo;
import io.vavr.control.Either;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.Scanner;

public class InsertANewItemInGivenPurchase {
    public static void main(String[] args) {
        SeContainerInitializer initializer = SeContainerInitializer.newInstance();
        final SeContainer container = initializer.initialize();
        ServicesPurchaseMongo sP = container.select(ServicesPurchaseMongo.class).get();

        Scanner sc = new Scanner(System.in);

        System.out.println("Introduce the id of the purchase where you want to add the item");
        String id = sc.nextLine();
        System.out.println("Introduce the name of the item");
        String itemName = sc.nextLine();
        System.out.println("Introduce the quantity");
        int quantity = sc.nextInt();
        sc.nextLine();
        System.out.println("Introduce the price");
        double price = Double.parseDouble(sc.nextLine());
        Either<String, Void> rAddItem = sP.addItemToPurchase(new Purchase(new ObjectId(id), List.of(new Item(quantity, itemName, price))));
        if (rAddItem.isLeft()) {
            System.out.println(rAddItem.getLeft());
        } else {
            System.out.println("Added");
        }
    }
}
