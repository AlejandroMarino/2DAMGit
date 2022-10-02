package modelo;

import com.squareup.moshi.Json;
import lombok.Data;

import java.util.List;

@Data
public class Availability {
    private boolean isAllYear;
    private boolean isAllDay;
    @Json(name = "month-array-northern")
    private List<Integer> monthArrayNorthern;
    @Json(name = "month-southern")
    private String monthSouthern;
    @Json(name = "month-array-southern")
    private List<Integer> monthArraySouthern;
    @Json(name = "month-northern")
    private String monthNorthern;
    private String location;
    private String time;
    @Json(name = "time-array")
    private List<Integer> timeArray;
    private String rarity;
}