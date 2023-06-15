package domain.services.servicesHibernate;

import dao.daoHibernate.DaoClientHibernate;
import dao.daoHibernate.DaoItemHibernate;
import dao.daoHibernate.DaoPurchaseHibernate;
import domain.model.modelHibernate.ClientHibernate;
import domain.model.modelHibernate.ItemHibernate;
import domain.model.modelHibernate.PurchaseHibernate;
import domain.model.modelHibernate.PurchasesItemsHibernate;
import io.vavr.control.Either;
import jakarta.inject.Inject;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ServicesPurchasesHibernate {

    private final DaoClientHibernate dC;

    private final DaoItemHibernate dI;

    private final DaoPurchaseHibernate dP;

    @Inject
    public ServicesPurchasesHibernate(DaoClientHibernate dC, DaoItemHibernate dI, DaoPurchaseHibernate dP) {
        this.dC = dC;
        this.dI = dI;
        this.dP = dP;
    }

    public String addPurchaseToAnne() {
        Either<Integer, ClientHibernate> rGetClient = dC.get("Anne");
        if (rGetClient.isLeft()) {
            return "Client not found";
        } else {
            Either<Integer, ItemHibernate> rGetItem1 = dI.get("milk");
            if (rGetItem1.isLeft()) {
                return "First item not found";
            } else {
                Either<Integer, ItemHibernate> rGetItem2 = dI.get("fish");
                if (rGetItem2.isLeft()) {
                    return "Second item not found";
                } else {
                    List<PurchasesItemsHibernate> items = new ArrayList<>();
                    PurchasesItemsHibernate pi1 = new PurchasesItemsHibernate(rGetItem1.get(), 1);
                    items.add(pi1);
                    PurchasesItemsHibernate pi2 = new PurchasesItemsHibernate(rGetItem2.get(), 1);
                    items.add(pi2);
                    double totalcost = (pi1.getItem().getPrice() * pi1.getAmount()) + (pi2.getItem().getPrice() * pi2.getAmount());
                    ClientHibernate c = rGetClient.get();
                    double oldBalance = c.getBalance();
                    double newBalance = oldBalance - totalcost;
                    if (newBalance < 0) {
                        return "Not enough money";
                    } else {
                        c.setBalance(newBalance);
                        PurchaseHibernate purchase = new PurchaseHibernate(rGetClient.get(), LocalDate.now(), totalcost, true, items);
                        Either<Integer, Void> rAdd = dP.save(purchase);
                        if (rAdd.isLeft()) {
                            return "Error while adding purchase";
                        } else {
                            return "Added";
                        }
                    }
                }
            }
        }
    }
}
