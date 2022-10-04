package modelo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Type {
    private int id;
    private String name;

    public Type(String line){
        String[] parts = line.split(";");
        this.id = Integer.parseInt(parts[0]);
        this.name = parts[1];
    }

    public String toString(){
        return id + ";" + name;
    }
}
