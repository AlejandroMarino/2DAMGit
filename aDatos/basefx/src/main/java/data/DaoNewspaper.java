package data;

import config.Configuracion;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import modelo.Newspaper;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class DaoNewspaper {
    private final Configuracion config;

    @Inject
    public DaoNewspaper(Configuracion config) {
        this.config = config;
    }

    public boolean delete(Newspaper n) {
        List<Newspaper> news = getAll().get();
        news.remove(n);
        BufferedWriter writer;
        Path p = Paths.get(config.getNewspapers());
        try {
            writer = Files.newBufferedWriter(p, StandardCharsets.UTF_8);
            for (Newspaper newspaper : news) {
                writer.write(newspaper.toString());
                writer.newLine();
            }
            writer.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
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
            reader.close();
            return Either.right(newspapers);
        } catch (IOException e) {
            return Either.left("No newspapers found");
        }
    }
}
