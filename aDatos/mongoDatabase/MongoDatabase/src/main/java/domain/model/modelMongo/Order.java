package domain.model.modelMongo;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Order {
    private String date;
    private Double total;
    private boolean paid;
    private Table table;

    @Singular
    private List<OrderItem> orderItems;

    public Order(String date, Double total, boolean paid) {
        this.date = date;
        this.total = total;
        this.paid = paid;
    }

    public Order(Table table, List<OrderItem> orderItems) {
        this.table = table;
        this.orderItems = orderItems;
    }
}
