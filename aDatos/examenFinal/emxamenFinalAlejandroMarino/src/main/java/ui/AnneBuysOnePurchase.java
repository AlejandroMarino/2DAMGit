package ui;

import domain.services.servicesHibernate.ServicesPurchasesHibernate;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;

public class AnneBuysOnePurchase {

    public static void main(String[] args) {
        SeContainerInitializer initializer = SeContainerInitializer.newInstance();
        final SeContainer container = initializer.initialize();
        ServicesPurchasesHibernate sP = container.select(ServicesPurchasesHibernate.class).get();

        System.out.println(sP.addPurchaseToAnne());
    }
}
