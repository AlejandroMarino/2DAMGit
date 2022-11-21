package data;

import config.DBConnectionPool;
import data.modelo.Newspaper;
import domain.modelo.BaseDatosCaidaException;
import domain.modelo.NotFoundException;
import jakarta.inject.Inject;
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

    public List<Newspaper> getAll() {
        try {
            JdbcTemplate jtm = new JdbcTemplate(dbConnectionPool.getDataSource());
            return jtm.query("SELECT * FROM newspaper", BeanPropertyRowMapper.newInstance(Newspaper.class));
        } catch (Exception e) {
            throw new BaseDatosCaidaException("Error en la base de datos");
        }
    }

    public Newspaper get(int id) {
        try {
            JdbcTemplate jtm = new JdbcTemplate(dbConnectionPool.getDataSource());
            return jtm.queryForObject("SELECT * FROM newspaper WHERE id = ?", BeanPropertyRowMapper.newInstance(Newspaper.class), id);
        } catch (Exception e) {
            throw new BaseDatosCaidaException("Error en la base de datos");
        }

    }

    public int add(Newspaper newspaper) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(dbConnectionPool.getDataSource()).withTableName("newspaper");
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name_newspaper", newspaper.getName());
        parameters.put("release_date", newspaper.getReleaseDate());
        return jdbcInsert.execute(parameters);
    }

    public void delete(int id) {
        TransactionDefinition def = new DefaultTransactionDefinition();
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(dbConnectionPool.getDataSource());
        TransactionStatus status = transactionManager.getTransaction(def);

        try {
            JdbcTemplate jtm = new JdbcTemplate(transactionManager.getDataSource());
            jtm.update("DELETE FROM readarticle WHERE id_article IN (SELECT id FROM article WHERE id_newspaper = ?)", id);
            jtm.update("DELETE FROM article WHERE id_newspaper = ?", id);
            jtm.update("DELETE FROM subscribe WHERE id_newspaper = ?", id);
            jtm.update("DELETE FROM newspaper WHERE id = ?", id);
            transactionManager.commit(status);
        } catch (Exception e) {
            transactionManager.rollback(status);
            throw new BaseDatosCaidaException("Base de datos caida");
        }
    }

    public Newspaper update(Newspaper newspaper) {
        JdbcTemplate jtm = new JdbcTemplate(dbConnectionPool.getDataSource());
        int r = jtm.update("UPDATE newspaper SET name_newspaper = ?, release_date = ? WHERE id = ?",
                newspaper.getName(), newspaper.getReleaseDate(), newspaper.getId());
        if (r < 1)
            throw new NotFoundException("No se ha podido actualizar el periódico");
        return newspaper;
    }
}
