package modelo;

import com.squareup.moshi.Json;
import lombok.Data;

@Data
public class Name{
	@Json(name = "name-EUru")
	private String name_EUru;
	@Json(name = "name-EUnl")
	private String name_EUnl;
	@Json(name = "name-EUde")
	private String name_EUde;
	@Json(name = "name-EUit")
	private String name_EUit;
	@Json(name = "name-JPja")
	private String name_JPja;
	@Json(name = "name-EUfr")
	private String name_EUfr;
	@Json(name = "name-EUen")
	private String name_EUen;
	@Json(name = "name-CNzh")
	private String name_CNzh;
	@Json(name = "name-TWzh")
	private String name_TWzh;
	@Json(name = "name-USfr")
	private String name_USfr;
	@Json(name = "name-USes")
	private String name_USes;
	@Json(name = "name-USen")
	private String name_USen;
	@Json(name = "name-EUes")
	private String name_EUes;
	@Json(name = "name-KRko")
	private String name_KRko;
}