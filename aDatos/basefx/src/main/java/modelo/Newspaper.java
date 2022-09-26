package modelo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Newspaper {
    private int id;
    private String name;
    private Double price;
    private String director;

    public Newspaper(String line) {
        String[] parts = line.split(";");
        this.id = Integer.parseInt(parts[0]);
        this.name = parts[1];
        this.price = Double.parseDouble(parts[2]);
        this.director = parts[3];
    }

    public String toString(){
        return id + ";" + name + ";" + price + ";" + director;
    }
}
