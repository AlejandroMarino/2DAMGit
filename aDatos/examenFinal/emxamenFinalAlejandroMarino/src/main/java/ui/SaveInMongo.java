package ui;

import domain.services.servicesHibernate.ServicesItemsHibernate;
import domain.services.servicesMongo.ServicesMongo;
import io.vavr.control.Either;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;

public class SaveInMongo {
    public static void main(String[] args) {
        SeContainerInitializer initializer = SeContainerInitializer.newInstance();
        final SeContainer container = initializer.initialize();
        ServicesMongo sM = container.select(ServicesMongo.class).get();

        Either<String, Void> rSaveInMongo = sM.saveMongo();
        if (rSaveInMongo.isLeft()) {
            System.out.println(rSaveInMongo.getLeft());
        } else {
            System.out.println("Saved in mongo");
        }
    }
}
