package modelo;

import com.squareup.moshi.Json;
import common.Constantes;
import lombok.Data;

import java.util.List;

@Data
public class Availability {
    private boolean isAllYear;
    private boolean isAllDay;
    @Json(name = Constantes.MONTH_ARRAY_NORTHERN)
    private List<Integer> monthArrayNorthern;
    @Json(name = Constantes.MONTH_SOUTHERN)
    private String monthSouthern;
    @Json(name = Constantes.MONTH_ARRAY_SOUTHERN)
    private List<Integer> monthArraySouthern;
    @Json(name = Constantes.MONTH_NORTHERN)
    private String monthNorthern;
    private String location;
    private String time;
    @Json(name = Constantes.TIME_ARRAY)
    private List<Integer> timeArray;
    private String rarity;
}