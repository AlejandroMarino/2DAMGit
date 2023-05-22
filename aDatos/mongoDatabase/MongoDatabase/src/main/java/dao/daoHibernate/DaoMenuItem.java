package dao.daoHibernate;

import domain.model.modelHibernate.MenuItem;
import io.vavr.control.Either;

import java.util.List;

public interface DaoMenuItem {

    Either<Integer, List<MenuItem>> getAll();

}
