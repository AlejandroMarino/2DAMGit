package dao;

import io.vavr.control.Either;
import model.db.Client;
import model.xml.ClientsXML;

import java.util.List;

public interface ClientsDAO {
    Either<Integer, List<Client>> getAll();

    int delete(int id, int confirmed);

    int add(ClientsXML clients);
}
