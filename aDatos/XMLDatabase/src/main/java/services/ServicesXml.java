package services;

import io.vavr.control.Either;

public interface ServicesXml {

    Either<String, Void> add();
}
