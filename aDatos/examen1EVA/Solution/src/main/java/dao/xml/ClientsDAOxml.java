package dao.xml;

import dao.ClientsDAO;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import model.db.Client;
import model.xml.ClientsXML;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class ClientsDAOxml implements ClientsDAO {

    private final config.Configuration config;

    @Inject
    public ClientsDAOxml(config.Configuration config) {
        this.config = config;
    }
    @Override
    public int add(ClientsXML clients){
        JAXBContext context;
        Marshaller marshaller;
        try {
            context = JAXBContext.newInstance(ClientsXML.class);
            marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        } catch (JAXBException e) {
            return -2;
        }
        Path xmlFile = Paths.get(config.getPathXML());
        try {
            marshaller.marshal(clients, Files.newOutputStream(xmlFile));
        } catch (JAXBException | IOException e) {
            return -1;
        }
        return 0;
    }

    public Either<Integer, List<Client>> getAll(){
        return null;
    }

    public int delete(int id, int confirmed) {
        return 0;
    }
}
