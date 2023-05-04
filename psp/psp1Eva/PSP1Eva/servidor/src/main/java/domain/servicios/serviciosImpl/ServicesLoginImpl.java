package domain.servicios.serviciosImpl;

import data.DaoLogin;
import domain.modelo.NotFoundException;
import domain.models.User;
import domain.servicios.ServicesLogin;
import io.jsonwebtoken.Jwts;
import jakarta.di.KeyProvider;
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
        KeyProvider k = new KeyProvider();
        try {
            Set<String> roles = getRoles(user.getUsername());
            return Jwts.builder()
                    .setSubject("Client")
                    .setIssuer("Server")
                    .claim("username", user.getUsername())
                    .claim("roles", roles)
                    .signWith(k.key()).compact();
        } catch (Exception e) {
            throw new NotFoundException("Error while generating JWS");
        }
    }
}
