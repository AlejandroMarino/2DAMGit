package domain.model.txt;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MenuItem {
    private int id;
    private String name;
    private String description;
    private double price;

    public MenuItem(String line){
        String[] parts = line.split(";");
        this.id = Integer.parseInt(parts[0]);
        this.name = parts[1];
        this.description = parts[2];
        this.price = Double.parseDouble(parts[3]);
    }

    public String toString(){
        return id + ";" + name + ";" + description + ";" + price;
    }
}
