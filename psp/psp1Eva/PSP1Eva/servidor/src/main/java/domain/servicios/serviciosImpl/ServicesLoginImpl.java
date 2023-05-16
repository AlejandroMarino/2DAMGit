package domain.servicios.serviciosImpl;

import data.DaoLogin;
import domain.models.User;
import domain.servicios.ServicesLogin;
import jakarta.inject.Inject;
import jakarta.security.GenerateToken;
import jakarta.security.enterprise.identitystore.Pbkdf2PasswordHash;

import java.util.List;

public class ServicesLoginImpl implements ServicesLogin {

    private final GenerateToken gT;
    private final DaoLogin daoLogin;
    private final Pbkdf2PasswordHash passwordHash;

    @Inject
    public ServicesLoginImpl(GenerateToken gT, DaoLogin daoLogin, Pbkdf2PasswordHash passwordHash) {
        this.gT = gT;
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
    public List<String> getRoles(String username) {
        return daoLogin.getRoles(username);
    }

    @Override
    public boolean validate(String code) {
        return daoLogin.validate(code);
    }

    @Override
    public boolean activated(String username) {
        User user = daoLogin.getUser(username);
        if (user != null) {
            return user.isActivated();
        } else {
            return false;
        }
    }

    @Override
    public String generateJWS(User user) {
        List<String> roles = getRoles(user.getUsername());
        return gT.generateToken(user.getUsername(), roles);
    }
}
