package domain.services.servicesHibernate;

import dao.daoHibernate.DaoClientHibernate;
import dao.daoHibernate.DaoItemHibernate;
import domain.model.modelHibernate.ClientHibernate;
import domain.model.modelHibernate.ItemHibernate;
import io.vavr.control.Either;
import jakarta.inject.Inject;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ServicesItemsHibernate {

    private final DaoItemHibernate dI;

    @Inject
    public ServicesItemsHibernate(DaoItemHibernate dI) {
        this.dI = dI;
    }

    public Either<String, Set<ItemHibernate>> getAllItemsPurchasedByAnne() {
        Either<Integer, List<ItemHibernate>> rGetItems = dI.getAll(new ClientHibernate("Anne"));
        if (rGetItems.isLeft()) {
            return Either.left("Error while getting items");
        } else {
            Set<ItemHibernate> items = new HashSet<>();
            items.addAll(rGetItems.get());
            return Either.right(items);
        }
    }
}
