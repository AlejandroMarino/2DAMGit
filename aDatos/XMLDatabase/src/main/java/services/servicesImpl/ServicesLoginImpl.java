package services.servicesImpl;

import dao.DaoLogin;
import jakarta.inject.Inject;
import services.ServicesLogin;

public class ServicesLoginImpl implements ServicesLogin {

    private final DaoLogin d;

    @Inject
    public ServicesLoginImpl(DaoLogin d) {
        this.d = d;
    }

    @Override
    public boolean login(String user, String password) {
        return d.login(user, password);
    }
}
