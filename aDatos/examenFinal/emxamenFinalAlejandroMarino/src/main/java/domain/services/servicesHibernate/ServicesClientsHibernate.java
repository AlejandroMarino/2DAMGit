package domain.services.servicesHibernate;

import dao.daoHibernate.DaoClientHibernate;
import domain.model.modelHibernate.ClientHibernate;
import io.vavr.control.Either;
import jakarta.inject.Inject;

public class ServicesClientsHibernate {

    private final DaoClientHibernate dC;

    @Inject
    public ServicesClientsHibernate(DaoClientHibernate dC) {
        this.dC = dC;
    }

    public Either<Integer, Void> delete(int id, boolean withPurchases) {
        return dC.delete(new ClientHibernate(id), withPurchases);
    }
}
