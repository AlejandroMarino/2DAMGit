package config;

import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener()
public class ListenerConfig implements ServletContextListener {

    DBConnectionPool pool;

    @Inject
    public ListenerConfig(@Named("db") DBConnectionPool pool) {
        this.pool = pool;
    }

    public ListenerConfig() {
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        pool.cargarPool();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        pool.closePool();
    }

}
