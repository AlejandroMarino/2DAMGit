package dao.daotxt.daoImpl;

import config.ConfigurationLogin;
import dao.daotxt.DaoLogin;
import jakarta.inject.Inject;

public class DaoLoginImpl implements DaoLogin {

    private final ConfigurationLogin config;

    @Inject
    public DaoLoginImpl(ConfigurationLogin config) {
        this.config = config;
    }

    @Override
    public boolean login(String user, String password) {
        return config.getUser().equals(user) && config.getPassword().equals(password);
    }
}
