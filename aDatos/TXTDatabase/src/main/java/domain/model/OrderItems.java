package domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
public class OrderItems {
    private int id;
    private int orderId;
    private int menuItemId;
    private int quantity;
    private double price;
}
