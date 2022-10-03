package data;

import config.Configuracion;
import io.vavr.control.Either;
import modelo.Newspaper;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class DaoNewspaper {
    private Configuracion config;

    public int save(List<Newspaper> newspapers) {
        return 0;
    }

    public int update(List<Newspaper> newspapers) {
        return 0;
    }

    public int delete(Newspaper n){
        return 0;
    }
    public Either<String, Newspaper> get(int id) {
        if (getAll().isLeft()) {
            return Either.left(getAll().getLeft());
        } else {
            return Either.right(getAll().get().stream().filter(newspaper -> newspaper.getId() == id).findFirst().orElse(null));
        }
    }

    public Either<String, List<Newspaper>> getAll() {
        List<Newspaper> newspapers = new ArrayList<>();
        BufferedReader reader;
        Path p = Paths.get(config.getNewspapers());
        try {
            reader = Files.newBufferedReader(p, StandardCharsets.UTF_8);
            reader.lines().forEach(line -> newspapers.add(new Newspaper(line)));
            return Either.right(newspapers);
        } catch (IOException e) {
            return Either.left("No newspapers found");
        }
    }
}
