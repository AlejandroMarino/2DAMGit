package domain.services.servicesMongo;

import dao.daoMongo.DaoPurchaseMongo;
import domain.model.modelMongo.Purchase;
import io.vavr.control.Either;
import jakarta.inject.Inject;

public class ServicesPurchaseMongo {

    private final DaoPurchaseMongo dP;

    @Inject
    public ServicesPurchaseMongo(DaoPurchaseMongo dP) {
        this.dP = dP;
    }

    public Either<String, Void> addItemToPurchase(Purchase p) {
        Either<Integer, Void> r = dP.update(p);
        if (r.isRight()) {
            return Either.right(null);
        } else if (r.getLeft() == -2) {
            return Either.left("Not Updated");
        } else {
            return Either.left("Error while updating");
        }
    }
}
