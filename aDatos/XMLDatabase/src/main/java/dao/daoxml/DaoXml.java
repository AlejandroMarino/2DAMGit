package dao.daoxml;

import domain.model.xml.Customers;
import io.vavr.control.Either;

public interface DaoXml {

    Either<Integer, Void> saveXml(Customers customers);

    Either<Integer, Customers> getAll();
}
