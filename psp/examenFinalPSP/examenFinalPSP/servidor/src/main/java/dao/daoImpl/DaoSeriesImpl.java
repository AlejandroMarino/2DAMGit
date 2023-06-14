package dao.daoImpl;

import dao.DaoSeries;
import domain.models.Serie;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DaoSeriesImpl implements DaoSeries {

    private static List<Serie> series = new ArrayList<>();

    static {
        series.add(new Serie(1, "Los anillos", LocalDate.now(), "Fantasia", 20));
        series.add(new Serie(2, "Los hombres de paco", LocalDate.now(), "Policiaca", 10));
        series.add(new Serie(3, "Aida", LocalDate.now(), "Comedia", 5));
    }

    @Override
    public List<Serie> getAll() {
        return series;
    }

    @Override
    public Serie getSerie(int id) {
        return series.stream().filter(serie -> serie.getId() == id).findFirst().orElse(null);
    }
}
