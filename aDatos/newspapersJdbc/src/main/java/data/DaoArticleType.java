package data;

import config.Configuracion;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import modelo.Type;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class DaoArticleType {

    private Configuracion config;

    @Inject
    public DaoArticleType(Configuracion config) {
        this.config = config;
    }

//    public Either<String, List<Type>> allTypes() {
//        List<Type> types = new ArrayList<>();
//        BufferedReader reader;
//        Path p = Paths.get(config.getTypes());
//        try {
//            reader = Files.newBufferedReader(p, StandardCharsets.UTF_8);
//            reader.lines().forEach(line -> types.add(new Type(line)));
//            reader.close();
//            return Either.right(types);
//        } catch (IOException e) {
//            return Either.left("No types found");
//        }
//    }

//    public Either<String,Type> getType(String name){
//        Type tipo = allTypes().get().stream().filter(type -> type.getName().equals(name)).findFirst().orElse(null);
//        if (tipo == null) {
//            return Either.left("No type found");
//        } else {
//            return Either.right(tipo);
//        }
//    }

//    public Either<String, Type> getTypeId(int id) {
//        Type tipo = allTypes().get().stream().filter(type -> type.getId() == id).findFirst().orElse(null);
//        if (tipo == null) {
//            return Either.left("No type found");
//        } else {
//            return Either.right(tipo);
//        }
//    }
}
