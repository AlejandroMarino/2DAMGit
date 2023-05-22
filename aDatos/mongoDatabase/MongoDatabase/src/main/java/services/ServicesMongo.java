package services;

import io.vavr.control.Either;

public interface ServicesMongo {
    Either<String, Void> saveMongo();
}
