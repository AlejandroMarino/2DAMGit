package dao.daoImpl;

import config.Configuration;
import dao.DaoXml;
import domain.model.xml.Customers;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DaoXmlImpl implements DaoXml {
    private final Configuration config;

    @Inject
    public DaoXmlImpl(Configuration config) {
        this.config = config;
    }

    public Either<Integer, Void> add(Customers customers) {
        JAXBContext context;
        Marshaller marshaller;
        try {
            context = JAXBContext.newInstance(Customers.class);
            marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        } catch (Exception e) {
            return Either.left(-2);
        }
        Path restaurant = Paths.get(config.getRestaurant());
        try {
            marshaller.marshal(customers, Files.newOutputStream(restaurant));
        } catch (Exception e) {
            return Either.left(-1);
        }
        return Either.right(null);
    }
}
