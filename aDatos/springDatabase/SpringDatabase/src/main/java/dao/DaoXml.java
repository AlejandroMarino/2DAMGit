package dao;

import domain.model.xml.Orders;
import io.vavr.control.Either;

public interface DaoXml {
    Either<Integer, Void> saveXml(Orders orders);

    Either<Integer, Orders> getAll();
}
