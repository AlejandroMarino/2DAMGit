package services;

import domain.model.spring.MenuItem;
import io.vavr.control.Either;

public interface ServicesMenuItems {

    Either<String, Void> modifyPrice(MenuItem menuItem);
}
