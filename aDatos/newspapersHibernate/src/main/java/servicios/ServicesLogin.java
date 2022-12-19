package servicios;

import data.DaoLogin;
import jakarta.inject.Inject;

public class ServicesLogin {
    private DaoLogin daoLogin;

    @Inject
    public ServicesLogin(DaoLogin daoLogin) {
        this.daoLogin = daoLogin;
    }

    public boolean login(String user, String password) {
        return daoLogin.login(user, password);
    }
}
