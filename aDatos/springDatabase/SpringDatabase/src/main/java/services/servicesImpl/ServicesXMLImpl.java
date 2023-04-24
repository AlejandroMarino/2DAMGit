package services.servicesImpl;

import dao.*;
import domain.model.spring.*;
import domain.model.xml.Orders;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import services.ServicesXML;

import java.util.ArrayList;
import java.util.List;

public class ServicesXMLImpl implements ServicesXML {

    private final DaoXml dX;
    private final DaoOrders dO;
    private final DaoCustomers dC;
    private final DaoTables dT;
    private final DaoOrderItems dOI;
    private final DaoMenuItem dMI;

    @Inject
    public ServicesXMLImpl(DaoXml dX, DaoOrders dO, DaoCustomers dC, DaoTables dT, DaoOrderItems dOI, DaoMenuItem dMI) {
        this.dX = dX;
        this.dO = dO;
        this.dC = dC;
        this.dT = dT;
        this.dOI = dOI;
        this.dMI = dMI;
    }

    @Override
    public Either<String, Void> savePaidOrders() {
        Either<Integer, List<Order>> lo = dO.getAll(true);
        List<domain.model.xml.Order> lXml = new ArrayList<>();
        if (lo.isLeft()) {
            return Either.left("There are no paid orders to save");
        } else {
            lo.get().forEach(order -> {
                Customer cus = dC.get(order.getCustomerId()).get();
                Table tab = dT.get(order.getTableId()).get();
                domain.model.xml.Customer c = new domain.model.xml.Customer(cus.getId(), cus.getFirstName(), cus.getLastName(), cus.getEmail(), cus.getPhone());
                domain.model.xml.Table t = new domain.model.xml.Table(tab.getId(), tab.getTableNumber(), tab.getNumberOfSeats());
                List<OrderItem> oI = dOI.getAll(order);
                List<domain.model.xml.OrderItem> lOI = new ArrayList<>();
                oI.forEach(orderItem -> {
                    MenuItem mI = dMI.get(orderItem);
                    domain.model.xml.MenuItem m = new domain.model.xml.MenuItem(mI.getId(), mI.getName(), mI.getDescription(), mI.getPrice());
                    domain.model.xml.OrderItem oi = new domain.model.xml.OrderItem(orderItem.getId(), orderItem.getQuantity(), m);
                    lOI.add(oi);
                });
                domain.model.xml.Order o = new domain.model.xml.Order(order.getId(), t, c, order.getOrderDate(), lOI);
                lXml.add(o);
            });
            Orders orders = new Orders(lXml);
            Either<Integer, Void> r = dX.saveXml(orders);
            if (r.isLeft()) {
                return Either.left("Error while saving orders");
            } else {
                lo.get().forEach(order -> {
                    dO.delete(order.getId());
                });
                return Either.right(null);
            }
        }
    }
}
