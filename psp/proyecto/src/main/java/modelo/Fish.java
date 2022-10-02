package modelo;

import com.squareup.moshi.Json;
import lombok.Data;

@Data
public class Fish {
    private int id;
    @Json(name = "file-name")
    private String file_name;
    private Name name;
    private Availability availability;
    private String shadow;
    private int price;
    @Json(name = "price-cj")
    private int price_Cj;
    @Json(name = "catch-phrase")
    private String catch_Phrase;
    @Json(name = "museum-phrase")
    private String museum_Phrase;
    private String image_uri;
    private String icon_uri;


    @Override
    public String toString() {
        return "Fish{" +
                "id=" + id +
                ", file name='" + file_name + '\'' +
                ", name=" + name +
                ", availability=" + availability +
                ", shadow='" + shadow + '\'' +
                ", price=" + price +
                ", price Cj=" + price_Cj +
                ", catch Phrase='" + catch_Phrase + '\'' +
                ", museum Phrase='" + museum_Phrase + '\'' +
                ", image url='" + image_uri + '\'' +
                ", icon url='" + icon_uri + '\'' +
                '}';
    }
}