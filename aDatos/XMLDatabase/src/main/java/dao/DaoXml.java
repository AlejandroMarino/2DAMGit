package dao;

import domain.model.xml.Customers;
import io.vavr.control.Either;

public interface DaoXml {

    Either<Integer, Void> add(Customers customers);
}
