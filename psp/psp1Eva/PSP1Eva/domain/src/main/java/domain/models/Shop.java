package domain.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Shop {
    private int id;
    private String name;

    public Shop(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
