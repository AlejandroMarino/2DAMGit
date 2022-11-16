package data;

import config.Configuration;
import jakarta.inject.Inject;

public class DaoLogin {
    private Configuration config;

    @Inject
    public DaoLogin(Configuration config) {
        this.config = config;
    }

    public boolean login(String user, String password) {
        return config.getUser().equals(user) && config.getPassword().equals(password);
    }
}