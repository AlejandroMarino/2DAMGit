package data;

import config.Configuracion;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import modelo.Article;
import modelo.Type;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DaoArticle {

    private final Configuracion config;

    @Inject
    public DaoArticle(Configuracion config) {
        this.config = config;
    }

    public Either<String, List<Article>> filter(Type type) {
        if (getAll().isLeft()) {
            return Either.left(getAll().getLeft());
        } else {
            List<Article> articles = getAll()
                    .get()
                    .stream()
                    .filter(article -> article.getType() == type.getId()).collect(Collectors.toUnmodifiableList());
            return Either.right(articles);
        }
    }

    public int addArt(Article article) {
        BufferedWriter writer;
        Path p = Paths.get(config.getArticles());
        if (getAll().isLeft()) {
            return -1;
        } else {
            if (availableId(article.getId())) {
                try {
                    writer = Files.newBufferedWriter(p, StandardOpenOption.APPEND);
                    writer.newLine();
                    writer.append(article.toString());
                    writer.close();
                    return 0;
                } catch (IOException e) {
                    e.printStackTrace();
                    return -1;
                }
            } else {
                return 1;
            }
        }
    }

    public Either<String, List<Article>> getAll() {
        List<Article> articles = new ArrayList<>();
        BufferedReader reader;
        Path p = Paths.get(config.getArticles());
        try {
            reader = Files.newBufferedReader(p, StandardCharsets.UTF_8);
            reader.lines().forEach(line -> articles.add(new Article(line)));
            reader.close();
            return Either.right(articles);
        } catch (IOException e) {
            return Either.left("No articles found");
        }
    }

    public boolean delete(Article a) {
        List<Article> articles = getAll().get();
        articles.remove(a);
        BufferedWriter writer;
        Path p = Paths.get(config.getArticles());
        try {
            writer = Files.newBufferedWriter(p, StandardCharsets.UTF_8);
            for (Article article : articles) {
                writer.newLine();
                writer.write(article.toString());
            }
            writer.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean availableId(int id) {
        return getAll().get().stream().noneMatch(article -> article.getId() == id);
    }

}

