package domain.servicios;

import data.DaoReader;
import data.modelo.Reader;
import jakarta.inject.Inject;

import java.util.List;

public class ServicesReader {

    private DaoReader daoReader;

    @Inject
    public ServicesReader(DaoReader daoReader) {
        this.daoReader = daoReader;
    }

    public List<Reader> getAll() {
        return daoReader.getAll();
    }

    public Reader get(int id) {
        return daoReader.getReader(id);
    }

    public Reader add(Reader reader) {
        return daoReader.add(reader);
    }

    public void delete(int id) {
        daoReader.delete(id);
    }

    public Reader update(Reader reader) {
        return daoReader.update(reader);
    }
}
