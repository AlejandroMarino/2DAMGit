package servicios;

import dao.DaoMarvel;
import dao.modelo.marvel.Character;

import java.util.List;

public class ServiciosMarvel {


    public List<Character> getCharacteres()
    {
        DaoMarvel dao = new DaoMarvel();
        return dao.getCharacters();
    }
}
