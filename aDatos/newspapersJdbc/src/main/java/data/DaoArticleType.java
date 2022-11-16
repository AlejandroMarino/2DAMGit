package data;

import config.DBConnectionPool;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import modelo.Type;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class DaoArticleType {

    private final DBConnectionPool db;

    @Inject
    public DaoArticleType(DBConnectionPool db) {
        this.db = db;
    }

    public Either<Integer, List<Type>> getAll() {
        try {
            JdbcTemplate jtm = new JdbcTemplate(db.getDataSource());
            return Either.right(jtm.query("SELECT * FROM type", BeanPropertyRowMapper.newInstance(Type.class)));
        } catch (Exception e) {
            return Either.left(-1);
        }
    }

    public Either<Integer, Type> get(int id) {
        try {
            JdbcTemplate jtm = new JdbcTemplate(db.getDataSource());
            return Either.right(jtm.queryForObject("SELECT * FROM type WHERE id = ?", BeanPropertyRowMapper.newInstance(Type.class), id));
        } catch (Exception e) {
            return Either.left(-1);
        }
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
