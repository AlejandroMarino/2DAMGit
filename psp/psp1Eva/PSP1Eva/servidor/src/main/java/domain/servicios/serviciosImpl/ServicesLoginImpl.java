package domain.servicios.serviciosImpl;

import data.DaoLogin;
import domain.models.User;
import domain.servicios.ServicesLogin;
import jakarta.inject.Inject;
import jakarta.security.enterprise.identitystore.Pbkdf2PasswordHash;

import java.util.HashSet;
import java.util.Set;

public class ServicesLoginImpl implements ServicesLogin {

    private final DaoLogin daoLogin;
    private final Pbkdf2PasswordHash passwordHash;

    @Inject
    public ServicesLoginImpl(DaoLogin daoLogin, Pbkdf2PasswordHash passwordHash) {
        this.daoLogin = daoLogin;
        this.passwordHash = passwordHash;
    }

    @Override
    public User register(User user) {
        return daoLogin.register(user);
    }

    @Override
    public User validationUser(String username, char[] password) {
        User user = daoLogin.getUser(username);
        if (user == null) {
            return null;
        } else {
            if (passwordHash.verify(password, user.getPassword())) {
                return user;
            } else {
                return null;
            }
        }
    }

    @Override
    public Set<String> getRoles(String username) {
        return new HashSet<>(daoLogin.getRoles(username));
    }

    @Override
    public boolean validate(String code) {
        return daoLogin.validate(code);
    }
}
