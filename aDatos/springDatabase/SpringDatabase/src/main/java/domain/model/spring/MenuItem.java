package domain.model.spring;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuItem {
    private int id;
    private String name;
    private String description;
    private double price;

    public MenuItem(String name) {
        this.name = name;
    }

    public MenuItem(String name, double price) {
        this.name = name;
        this.price = price;
    }
}
