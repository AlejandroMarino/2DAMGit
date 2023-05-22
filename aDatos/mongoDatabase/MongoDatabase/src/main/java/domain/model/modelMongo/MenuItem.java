package domain.model.modelMongo;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class MenuItem {

    private int _id;
    private String name;
    private String description;
    private double price;
}
