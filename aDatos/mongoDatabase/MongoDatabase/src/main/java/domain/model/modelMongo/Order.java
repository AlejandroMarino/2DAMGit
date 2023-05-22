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
}
