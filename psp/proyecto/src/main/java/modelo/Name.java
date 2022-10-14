package modelo;

import com.squareup.moshi.Json;
import common.Constantes;
import lombok.Data;

@Data
public class Name{
	@Json(name = Constantes.NAME_EURU)
	private String name_EUru;
	@Json(name = Constantes.NAME_EUNL)
	private String name_EUnl;
	@Json(name = Constantes.NAME_EUDE)
	private String name_EUde;
	@Json(name = Constantes.NAME_EUIT)
	private String name_EUit;
	@Json(name = Constantes.NAME_JPJA)
	private String name_JPja;
	@Json(name = Constantes.NAME_EUFR)
	private String name_EUfr;
	@Json(name = Constantes.NAME_EUEN)
	private String name_EUen;
	@Json(name = Constantes.NAME_CNZH)
	private String name_CNzh;
	@Json(name = Constantes.NAME_TWZH)
	private String name_TWzh;
	@Json(name = Constantes.NAME_USFR)
	private String name_USfr;
	@Json(name = Constantes.NAME_USES)
	private String name_USes;
	@Json(name = Constantes.NAME_USEN)
	private String name_USen;
	@Json(name = Constantes.NAME_EUES)
	private String name_EUes;
	@Json(name = Constantes.NAME_KRKO)
	private String name_KRko;
}