package data;

import config.DBConnectionPool;
import jakarta.inject.Inject;

public class DaoReadArticle {
    private DBConnectionPool db;

    @Inject
    public DaoReadArticle(DBConnectionPool db) {
        this.db = db;
    }


}
