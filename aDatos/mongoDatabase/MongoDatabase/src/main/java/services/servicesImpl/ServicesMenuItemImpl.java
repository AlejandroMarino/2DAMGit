package services.servicesImpl;

import dao.daoMongo.DaoMenuItems;
import dao.daoMongo.DaoOrderItem;
import domain.model.modelMongo.Customer;
import domain.model.modelMongo.MenuItem;
import domain.model.modelMongo.OrderItem;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import services.ServicesMenuItem;

import java.util.List;
import java.util.Random;

public class ServicesMenuItemImpl implements ServicesMenuItem {

    private final DaoMenuItems dMI;
    private final DaoOrderItem dOI;

    @Inject
    public ServicesMenuItemImpl(DaoMenuItems dMI, DaoOrderItem dOI) {
        this.dMI = dMI;
        this.dOI = dOI;
    }


    @Override
    public Either<String, List<MenuItem>> getAllOfCustomer(Customer customer) {
        Either<Integer, List<MenuItem>> r = dMI.getAll(customer);
        if (r.isLeft()) {
            return Either.left("Error getting menu items");
        } else {
            return Either.right(r.get());
        }
    }

    @Override
    public Either<String, Void> add(MenuItem menuItem) {
        Random random = new Random();
        menuItem.set_id(random.nextInt(Integer.MAX_VALUE));
        Either<Integer, Void> r = dMI.save(menuItem);
        if (r.isLeft()) {
            return Either.left("Error adding menu item");
        } else {
            return Either.right(r.get());
        }
    }

    @Override
    public Either<Integer, Void> delete(int id, boolean withOrderItems) {
        Either<Integer, List<OrderItem>> r = dOI.getAll(new MenuItem(id));
        if ((r.isLeft() && r.getLeft() == -2) || (r.isRight() && r.get().isEmpty())) {
            Either<Integer, Void> r2 = dMI.delete(id, false);
            if (r2.isLeft()) {
                return Either.left(-1);
            } else {
                return Either.right(r2.get());
            }
        } else if (withOrderItems) {
            Either<Integer, Void> r2 = dMI.delete(id, true);
            if (r2.isLeft()) {
                return Either.left(-1);
            } else {
                return Either.right(r2.get());
            }
        } else {
            return Either.left(-2);
        }
    }
}
