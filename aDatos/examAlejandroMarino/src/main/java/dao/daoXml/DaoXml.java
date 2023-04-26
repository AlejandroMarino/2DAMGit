package dao.daoXml;

import config.Configuration;
import domain.modelXml.Usuarios;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DaoXml {
    private final Configuration config;

    @Inject
    public DaoXml(Configuration config) {
        this.config = config;
    }


    public Either<Integer, Void> saveXml(Usuarios usuarios) {
        JAXBContext context;
        Marshaller marshaller;
        try {
            context = JAXBContext.newInstance(Usuarios.class);
            marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            Path users = Paths.get(config.getUsers());
            marshaller.marshal(usuarios, Files.newOutputStream(users));

        } catch (Exception e) {
            e.printStackTrace();
            return Either.left(-1);
        }
        return Either.right(null);
    }
}
