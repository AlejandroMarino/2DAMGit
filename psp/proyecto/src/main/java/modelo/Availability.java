package modelo;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class Availability{
	private boolean isAllYear;
	private boolean isAllDay;
	private ArrayList<Integer> monthArrayNorthern;
	private String monthSouthern;
	private ArrayList<Integer> monthArraySouthern;
	private String monthNorthern;
	private String location;
	private String time;
	private ArrayList<Integer> timeArray;
	private String rarity;
}