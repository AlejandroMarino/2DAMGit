package data;

import config.DBConnectionPool;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import modelo.Article;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DaoArticle {

    private final DBConnectionPool db;

    @Inject
    public DaoArticle(DBConnectionPool db) {
        this.db = db;
    }

    public Either<Integer, List<Article>> getAll() {
        try {
            JdbcTemplate jtm = new JdbcTemplate(db.getDataSource());
            return Either.right(jtm.query("SELECT * FROM article", BeanPropertyRowMapper.newInstance(Article.class)));
        } catch (Exception e) {
            return Either.left(-1);
        }
    }

    public Either<Integer,Article> get(int id) {
        try {
            JdbcTemplate jtm = new JdbcTemplate(db.getDataSource());
            return Either.right(jtm.queryForObject("SELECT * FROM article WHERE id = ?", BeanPropertyRowMapper.newInstance(Article.class), id));
        }catch (Exception e){
            return Either.left(-1);
        }
    }

    public int add(Article article) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(db.getDataSource()).withTableName("article");
        Map<String, Object> parameters = new HashMap<>();

        parameters.put("name_article", article.getName());
        parameters.put("id_type", article.getType());
        parameters.put("id_newspaper", article.getIdNewspaper());
        return jdbcInsert.execute(parameters);
    }

    public int delete(int id) {
        int res = -1;
        TransactionDefinition def = new DefaultTransactionDefinition();
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(db.getDataSource());
        TransactionStatus status = transactionManager.getTransaction(def);

        try {
            JdbcTemplate jtm = new JdbcTemplate(transactionManager.getDataSource());
            jtm.update("DELETE FROM article WHERE id = ?", id);
            res = jtm.update("DELETE FROM readarticle WHERE id_article = ?", id);
            transactionManager.commit(status);
        } catch (Exception e) {
            transactionManager.rollback(status);
            throw e;
        }
        return res;
    }

    public int update(Article article) {
        JdbcTemplate jtm = new JdbcTemplate(db.getDataSource());
        return jtm.update("UPDATE article SET name_article = ?, id_type = ?, id_newspaper = ? WHERE id = ?",
                article.getName(), article.getType(), article.getIdNewspaper(), article.getId());
    }


//    public int addArt(Article article) {
//        BufferedWriter writer;
//        Path p = Paths.get(config.getArticles());
//        if (getAll().isLeft()) {
//            return -1;
//        } else {
//            if (availableId(article.getId())) {
//                try {
//                    writer = Files.newBufferedWriter(p, StandardOpenOption.APPEND);
//                    writer.newLine();
//                    writer.append(article.toString());
//                    writer.close();
//                    return 0;
//                } catch (IOException e) {
//                    e.printStackTrace();
//                    return -1;
//                }
//            } else {
//                return 1;
//            }
//        }
//    }

//    public Either<String, List<Article>> getAll() {
//        List<Article> articles = new ArrayList<>();
//        BufferedReader reader;
//        Path p = Paths.get(config.getArticles());
//        try {
//            reader = Files.newBufferedReader(p, StandardCharsets.UTF_8);
//            reader.lines().forEach(line -> articles.add(new Article(line)));
//            reader.close();
//            return Either.right(articles);
//        } catch (IOException e) {
//            return Either.left("No articles found");
//        }
//    }

//    public boolean delete(Article a) {
//        List<Article> articles = getAll().get();
//        articles.remove(a);
//        BufferedWriter writer;
//        Path p = Paths.get(config.getArticles());
//        try {
//            writer = Files.newBufferedWriter(p, StandardCharsets.UTF_8);
//            for (Article article : articles) {
//                writer.newLine();
//                writer.write(article.toString());
//            }
//            writer.close();
//            return true;
//        } catch (IOException e) {
//            e.printStackTrace();
//            return false;
//        }
//    }

//    private boolean availableId(int id) {
//        return getAll().get().stream().noneMatch(article -> article.getId() == id);
//    }

}

