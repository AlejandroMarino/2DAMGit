package data;

import config.Configuration;
import jakarta.inject.Inject;

public class DaoArticleType {

    private Configuration config;

    @Inject
    public DaoArticleType(Configuration config) {
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
