package domain.services.servicesMongo;

import dao.daoHibernate.DaoPurchaseHibernate;
import dao.daoMongo.DaoMongo;
import domain.model.modelHibernate.ClientHibernate;
import domain.model.modelHibernate.ItemHibernate;
import domain.model.modelHibernate.PurchaseHibernate;
import domain.model.modelMongo.Client;
import domain.model.modelMongo.Item;
import domain.model.modelMongo.Purchase;
import io.vavr.control.Either;
import jakarta.inject.Inject;

import java.util.ArrayList;
import java.util.List;

public class ServicesMongo {
    private final DaoPurchaseHibernate dP;
    private final DaoMongo dM;

    @Inject
    public ServicesMongo(DaoPurchaseHibernate dP, DaoMongo dM) {
        this.dP = dP;
        this.dM = dM;
    }

    public Either<String, Void> saveMongo() {
        Either<Integer, List<PurchaseHibernate>> rGetPurchases = dP.getAll();
        if (rGetPurchases.isLeft()) {
            return Either.left("Error while getting all purchases");
        } else {
            List<PurchaseHibernate> purchasesHibernate = rGetPurchases.get();
            List<Purchase> purchases = new ArrayList<>();
            purchasesHibernate.forEach(ph -> {
                List<Item> items = new ArrayList<>();
                ph.getItems().forEach(pih -> {
                    ItemHibernate i = pih.getItem();
                    Item item = new Item(pih.getAmount(), i.getName(), i.getPrice());
                    items.add(item);
                });
                ClientHibernate c = ph.getClient();
                Client client = new Client(c.getName(), c.getBalance());
                Purchase purchase = new Purchase(ph.getDate().toString(), client, items);
                purchases.add(purchase);
            });
            Either<Integer, Void> rAddToMongo = dM.save(purchases);
            if (rAddToMongo.isLeft()) {
                return Either.left("Error while adding purchases to mongo");
            } else {
                return Either.right(null);
            }
        }
    }
}
