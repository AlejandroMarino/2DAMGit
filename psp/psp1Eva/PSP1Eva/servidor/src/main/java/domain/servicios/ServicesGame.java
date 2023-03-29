package domain.servicios;

import data.DaoGame;
import domain.modelo.Newspaper;
import jakarta.inject.Inject;

import java.util.List;

public class ServicesGame {
    private final DaoGame daoGame;

    @Inject
    public ServicesGame(DaoGame daoGame) {
        this.daoGame = daoGame;
    }

    public List<Newspaper> getAll() {
        return daoGame.getAll();
    }

    public Newspaper get(int id) {
        return daoGame.get(id);
    }

    public boolean add(Newspaper newspaper) {
        return daoGame.add(newspaper) == 1;
    }

    public void delete(int id) {
        daoGame.delete(id);
    }

    public Newspaper update(Newspaper n) {
        return daoGame.update(n);
    }
}
