package domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
public class MenuItems {
    private int id;
    private String name;
    private String description;
    private double price;
}
