package model.db;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Purchases_items {
    private int id;
    private Purchase purchase;
    private Item item;
    private int amount;

    // when it is not added to the db, we do not have the purchase id
    public Purchases_items(Item item, int amount) {
        this.item = item;
        this.amount = amount;
    }

    //for ex 5
    public Purchases_items(int id, int amount) {
        this.id = id;
        this.amount = amount;
    }

    public Purchases_items(Purchase purchase, Item item) {
        this.purchase = purchase;
        this.item = item;
    }

    public Purchases_items(Purchase purchase, Item item, int amount) {
        this.purchase = purchase;
        this.item = item;
        this.amount = amount;
    }
}
