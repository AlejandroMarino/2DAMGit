package modelo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Article {
    private int id;
    private String name;
    private String description;
    private int type;
    private int idNewspaper;


    public Article(String line){
        String[] parts = line.split(";");
        this.id = Integer.parseInt(parts[0]);
        this.name = parts[1];
        this.description = parts[2];
        this.type = Integer.parseInt(parts[3]);
        this.idNewspaper = Integer.parseInt(parts[4]);
    }

    public String toString(){
        return id + ";" + name + ";" + description + ";" + type + ";" + idNewspaper;
    }
}
