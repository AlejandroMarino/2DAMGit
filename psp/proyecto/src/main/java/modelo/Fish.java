package modelo;

import com.squareup.moshi.Json;
import common.Constantes;
import lombok.Data;

@Data
public class Fish {
    private int id;
    @Json(name = Constantes.FILE_NAME)
    private String file_name;
    private Name name;
    private Availability availability;
    private String shadow;
    private int price;
    @Json(name = Constantes.PRICE_CJ)
    private int price_Cj;
    @Json(name = Constantes.CATCH_PHRASE)
    private String catch_Phrase;
    @Json(name = Constantes.MUSEUM_PHRASE)
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