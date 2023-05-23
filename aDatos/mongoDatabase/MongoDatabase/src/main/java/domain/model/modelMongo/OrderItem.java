package domain.model.modelMongo;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class OrderItem {
    private int menuItemId;
    private String name;
    private int quantity;

    public OrderItem(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
    }
}
