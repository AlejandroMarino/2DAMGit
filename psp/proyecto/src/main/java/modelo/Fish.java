package modelo;

import lombok.Data;

@Data
public class Fish{
	private int id;
	private String fileName;
	private Name name;
	private Availability availability;
	private String shadow;
	private int price;
	private int priceCj;
	private String catchPhrase;
	private String museumPhrase;
	private String imageUrl;
	private String iconUrl;
}