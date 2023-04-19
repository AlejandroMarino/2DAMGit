package dao.daoImpl;

import config.Configuration;
import dao.DaoXml;
import domain.model.xml.Orders;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DaoXmlImpl implements DaoXml {

    private Configuration config;

    @Inject
    public DaoXmlImpl(Configuration config) {
        this.config = config;
    }

    @Override
    public Either<Integer, Void> saveXml(Orders orders) {
        JAXBContext context;
        Marshaller marshaller;
        try {
            context = JAXBContext.newInstance(Orders.class);
            marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            Path paidOrders = Paths.get(config.getPaidOrders());
            marshaller.marshal(orders, Files.newOutputStream(paidOrders));

        } catch (Exception e) {
            e.printStackTrace();
            return Either.left(-1);
        }
        return Either.right(null);
    }

    @Override
    public Either<Integer, Orders> getAll() {
        JAXBContext context;
        Unmarshaller unmarshaller;
        try{
            context = JAXBContext.newInstance(Orders.class);
            unmarshaller = context.createUnmarshaller();

            Path restaurant = Paths.get(config.getPaidOrders());
            Orders orders = (Orders) unmarshaller.unmarshal(Files.newInputStream(restaurant));
            return Either.right(orders);
        } catch (Exception e) {
            e.printStackTrace();
            return Either.left(-2);
        }
    }
}
