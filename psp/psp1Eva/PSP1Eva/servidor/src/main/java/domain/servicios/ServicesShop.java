package domain.servicios;

import data.DaoShop;
import domain.modelo.Reader;
import jakarta.inject.Inject;

import java.util.List;

public class ServicesShop {

    private DaoShop daoShop;

    @Inject
    public ServicesShop(DaoShop daoShop) {
        this.daoShop = daoShop;
    }

    public List<Reader> getAll() {
        return daoShop.getAll();
    }

    public Reader get(int id) {
        return daoShop.get(id);
    }

    public Reader add(Reader reader) {
        return daoShop.add(reader);
    }

    public void delete(int id) {
        daoShop.delete(id);
    }

    public Reader update(Reader reader) {
        return daoShop.update(reader);
    }
}
