package data;

import config.Configuracion;
import io.vavr.control.Either;
import modelo.Newspaper;

import java.io.BufferedReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class DaoNewspaper {
    Configuracion config;

    public int save(List<Newspaper> newspapers) {
        return 0;
    }

    public int update(List<Newspaper> newspapers) {
        return 0;
    }

    public int get(int id) {
        return 0;
    }

    public Either<String, List<Newspaper>> getAll() {
        List<Newspaper> newspapers = null;
        BufferedReader reader = null;
        try {
            reader = Files.newBufferedReader(Paths.get(config.getNewspapers()), StandardCharsets.UTF_8);
            reader.lines().forEach(line -> newspapers.add(new Newspaper(line)));
            return Either.right(newspapers);
        }catch (Exception e){
            return Either.left("No newspapers found");
        }
    }
}
