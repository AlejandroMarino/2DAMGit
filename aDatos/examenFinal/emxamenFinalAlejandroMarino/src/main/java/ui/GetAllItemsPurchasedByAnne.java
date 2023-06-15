package ui;

import domain.model.modelHibernate.ItemHibernate;
import domain.services.servicesHibernate.ServicesItemsHibernate;
import io.vavr.control.Either;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;

import java.util.Set;

public class GetAllItemsPurchasedByAnne {

    public static void main(String[] args) {
        SeContainerInitializer initializer = SeContainerInitializer.newInstance();
        final SeContainer container = initializer.initialize();
        ServicesItemsHibernate sI = container.select(ServicesItemsHibernate.class).get();

        Either<String, Set<ItemHibernate>> rGetAll = sI.getAllItemsPurchasedByAnne();
        if (rGetAll.isLeft()) {
            System.out.println(rGetAll.getLeft());
        } else {
            rGetAll.get().forEach(System.out::println);
        }
    }
}
