package services;

import io.vavr.control.Either;

public interface ServicesXML {

    Either<String, Void> savePaidOrders();
}
