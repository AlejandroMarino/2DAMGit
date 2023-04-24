package services.servicesImpl;

import dao.DaoLogin;
import domain.model.spring.Login;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import services.ServicesLogin;

public class ServicesLoginImpl implements ServicesLogin {

    private DaoLogin daoLogin;

    @Inject
    public ServicesLoginImpl(DaoLogin daoLogin) {
        this.daoLogin = daoLogin;
    }


    @Override
    public Either<String, Boolean> doLogin(Login login) {
        Either<Integer, Login> l = daoLogin.get(login.getUsername());
        if (l.isLeft()) {
            return Either.left("User not found");
        } else {
            if (l.get().getPassword().equals(login.getPassword())) {
                return Either.right(l.get().getRole().trim().equalsIgnoreCase("admin"));
            } else {
                return Either.left("Wrong password");
            }
        }
    }

    @Override
    public void register(Login login) {
        if (login.getRole() == null){
            login.setRole("user");
        }
        daoLogin.save(login);
    }
}
