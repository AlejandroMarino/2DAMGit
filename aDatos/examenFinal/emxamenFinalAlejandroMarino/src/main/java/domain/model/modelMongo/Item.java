package domain.model.modelMongo;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Item {
    private int amount;
    private String name;
    private double price;
}
