package dao.daoImpl;

import dao.DaoArmas;
import domain.models.Arma;

import java.util.ArrayList;
import java.util.List;

public class DaoArmasImpl implements DaoArmas {

    private static List<Arma> armas = new ArrayList<>();

    static {
        armas.add(new Arma(1, "Espada", 1));
        armas.add(new Arma(2, "Arco", 1));
        armas.add(new Arma(3, "Baston", 1));
        armas.add(new Arma(4, "Pistola", 2));
        armas.add(new Arma(5, "Chiste", 3));
    }

    @Override
    public void addArma(Arma arma) {
        armas.add(arma);
    }

    @Override
    public Arma getArma(int id) {
        return armas.stream().filter(arma -> arma.getId() == id).findFirst().orElse(null);
    }

    @Override
    public List<Arma> getAll() {
        return armas;
    }

    @Override
    public void update(Arma arma) {
        armas.removeIf(arma1 -> arma1.getId() == arma.getId());
        armas.add(arma);
    }

    @Override
    public void deleteArma(int id) {
        armas.removeIf(arma -> arma.getId() == id);
    }


}
