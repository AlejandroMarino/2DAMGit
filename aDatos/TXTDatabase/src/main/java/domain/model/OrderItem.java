package domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderItem {
    private int id;
    private int orderId;
    private int menuItemId;
    private int quantity;
    private double price;

    public OrderItem(String line){
        String[] parts = line.split(";");
        this.id = Integer.parseInt(parts[0]);
        this.orderId = Integer.parseInt(parts[1]);
        this.menuItemId = Integer.parseInt(parts[2]);
        this.quantity = Integer.parseInt(parts[3]);
        this.price = Double.parseDouble(parts[4]);
    }

    public String toString(){
        return id + ";" + orderId + ";" + menuItemId + ";" + quantity + ";" + price;
    }
}
