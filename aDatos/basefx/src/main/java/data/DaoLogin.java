package data;

import config.Configuracion;
import jakarta.inject.Inject;

public class DaoLogin {
    private Configuracion config;

    @Inject
    public DaoLogin(Configuracion config) {
        this.config = config;
    }

    public boolean login(String user, String password) {
        return config.getUser().equals(user) && config.getPassword().equals(password);
    }
}