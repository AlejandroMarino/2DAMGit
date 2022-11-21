package domain.servicios;

import data.DaoNewspaper;
import domain.modelo.Newspaper;
import jakarta.inject.Inject;

import java.util.List;

public class ServicesNewspaper {
    private final DaoNewspaper daoNewspaper;

    @Inject
    public ServicesNewspaper(DaoNewspaper daoNewspaper) {
        this.daoNewspaper = daoNewspaper;
    }

    public List<Newspaper> getAll() {
        return daoNewspaper.getAll();
    }

    public Newspaper get(int id) {
        return daoNewspaper.get(id);
    }

    public boolean add(Newspaper newspaper) {
        return daoNewspaper.add(newspaper) == 1;
    }

    public void delete(int id) {
        daoNewspaper.delete(id);
    }

    public Newspaper update(Newspaper n) {
        return daoNewspaper.update(n);
    }
}
