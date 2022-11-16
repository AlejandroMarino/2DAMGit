package data;

import config.DBConnectionPool;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import modelo.Newspaper;
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

public class DaoNewspaper {
    private DBConnectionPool dbConnectionPool;

    @Inject
    public DaoNewspaper(DBConnectionPool dbConnectionPool) {
        this.dbConnectionPool = dbConnectionPool;
    }

    public Either<Integer, List<Newspaper>> getAll() {
        try {
            JdbcTemplate jtm = new JdbcTemplate(dbConnectionPool.getDataSource());
            return Either.right(jtm.query("SELECT * FROM newspaper", BeanPropertyRowMapper.newInstance(Newspaper.class)));
        } catch (Exception e) {
            return Either.left(-1);
        }
    }

    public Either<Integer, Newspaper> get(int id) {
        try {
            JdbcTemplate jtm = new JdbcTemplate(dbConnectionPool.getDataSource());
            return Either.right(jtm.queryForObject("SELECT * FROM newspaper WHERE id = ?", BeanPropertyRowMapper.newInstance(Newspaper.class), id));
        } catch (Exception e) {
            return Either.left(-1);
        }

    }

    public int add(Newspaper newspaper) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(dbConnectionPool.getDataSource()).withTableName("newspaper");
        Map<String, Object> parameters = new HashMap<>();

        parameters.put("name_newspaper", newspaper.getName());
        parameters.put("release_date", newspaper.getReleaseDate());
        return jdbcInsert.execute(parameters);
    }

    public int delete(int id) {
        int res = -1;
        TransactionDefinition def = new DefaultTransactionDefinition();
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(dbConnectionPool.getDataSource());
        TransactionStatus status = transactionManager.getTransaction(def);

        try {
            JdbcTemplate jtm = new JdbcTemplate(transactionManager.getDataSource());
            jtm.update("DELETE FROM readarticle WHERE id_article IN (SELECT id FROM article WHERE id_newspaper = ?)", id);
            jtm.update("DELETE FROM article WHERE id_newspaper = ?", id);
            jtm.update("DELETE FROM subscribe WHERE id_newspaper = ?", id);
            res = jtm.update("DELETE FROM newspaper WHERE id = ?", id);
            transactionManager.commit(status);
        } catch (Exception e) {
            transactionManager.rollback(status);
            throw e;
        }
        return res;
    }

    public int update(Newspaper newspaper) {
        JdbcTemplate jtm = new JdbcTemplate(dbConnectionPool.getDataSource());
        return jtm.update("UPDATE newspaper SET name_newspaper = ?, release_date = ? WHERE id = ?",
                newspaper.getName(), newspaper.getReleaseDate(), newspaper.getId());
    }

    //    public boolean delete(Newspaper n) {
//        List<Newspaper> news = getAll().get();
//        news.remove(n);
//        BufferedWriter writer;
//        Path p = Paths.get(config.getNewspapers());
//        try {
//            writer = Files.newBufferedWriter(p, StandardCharsets.UTF_8);
//            for (Newspaper newspaper : news) {
//                writer.write(newspaper.toString());
//                writer.newLine();
//            }
//            writer.close();
//            return true;
//        } catch (IOException e) {
//            e.printStackTrace();
//            return false;
//        }
//    }

//    public Either<String, Newspaper> get(int id) {
//        if (getAll().isLeft()) {
//            return Either.left(getAll().getLeft());
//        } else {
//            return Either.right(getAll().get().stream().filter(newspaper -> newspaper.getId() == id).findFirst().orElse(null));
//        }
//    }

//    public Either<String, List<Newspaper>> getAll() {
//        List<Newspaper> newspapers = new ArrayList<>();
//        BufferedReader reader;
//        Path p = Paths.get(config.getNewspapers());
//        try {
//            reader = Files.newBufferedReader(p, StandardCharsets.UTF_8);
//            reader.lines().forEach(line -> newspapers.add(new Newspaper(line)));
//            reader.close();
//            return Either.right(newspapers);
//        } catch (IOException e) {
//            return Either.left("No newspapers found");
//        }
//    }
}
