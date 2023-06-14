package domain.services.servicesImpl;

import dao.DaoSeries;
import domain.models.Serie;
import domain.services.ServicesSeries;
import jakarta.inject.Inject;

import java.util.List;

public class ServicesSeriesImpl implements ServicesSeries {

    private final DaoSeries dS;

    @Inject
    public ServicesSeriesImpl(DaoSeries dS) {
        this.dS = dS;
    }

    @Override
    public List<Serie> getAll() {
        return dS.getAll();
    }
}
