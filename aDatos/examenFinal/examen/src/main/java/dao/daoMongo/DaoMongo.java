package dao.daoMongo;

import com.google.gson.Gson;
import com.mongodb.client.MongoCollection;
import config.Configuration;
import jakarta.inject.Inject;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class DaoMongo {

    private final Configuration config;
    private final Gson gson;

    @Inject
    public DaoMongo(Configuration config, Gson gson) {
        this.config = config;
        this.gson = gson;
    }

    public void save(List<Ubicacion> ubicaciones) {
        List<Document> documents = new ArrayList<>();
        ubicaciones.forEach(u -> {
            Document doc = Document.parse(gson.toJson(u));
            documents.add(doc);
        });
        MongoCollection<Document> coll = config.getDb().getCollection("locations");
        coll.insertMany(documents);
    }
}
