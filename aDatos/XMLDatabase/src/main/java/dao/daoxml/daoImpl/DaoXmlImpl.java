package dao.daoxml.daoImpl;

import config.Configuration;
import dao.daoxml.DaoXml;
import domain.model.xml.Customers;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DaoXmlImpl implements DaoXml {
    private final Configuration config;

    @Inject
    public DaoXmlImpl(Configuration config) {
        this.config = config;
    }

    @Override
    public Either<Integer, Void> saveXml(Customers customers) {
        JAXBContext context;
        Marshaller marshaller;
        try {
            context = JAXBContext.newInstance(Customers.class);
            marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            Path restaurant = Paths.get(config.getRestaurant());
            marshaller.marshal(customers, Files.newOutputStream(restaurant));

        } catch (Exception e) {
            e.printStackTrace();
            return Either.left(-1);
        }
        return Either.right(null);
    }

    @Override
    public Either<Integer, Customers> getAll() {
        JAXBContext context;
        Unmarshaller unmarshaller;
        try{
            context = JAXBContext.newInstance(Customers.class);
            unmarshaller = context.createUnmarshaller();

            Path restaurant = Paths.get(config.getRestaurant());
            Customers customers = (Customers) unmarshaller.unmarshal(Files.newInputStream(restaurant));
            return Either.right(customers);
        } catch (Exception e) {
            e.printStackTrace();
            return Either.left(-2);
        }
    }
}
