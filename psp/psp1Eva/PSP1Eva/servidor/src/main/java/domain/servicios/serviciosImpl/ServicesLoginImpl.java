package domain.servicios.serviciosImpl;

import common.Constants;
import data.DaoLogin;
import domain.modelo.NotFoundException;
import domain.models.User;
import domain.servicios.ServicesLogin;
import io.jsonwebtoken.Jwts;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.security.enterprise.identitystore.Pbkdf2PasswordHash;

import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public class ServicesLoginImpl implements ServicesLogin {

    private final Key key;
    private final DaoLogin daoLogin;
    private final Pbkdf2PasswordHash passwordHash;

    @Inject
    public ServicesLoginImpl(@Named(Constants.JWT) Key key, DaoLogin daoLogin, Pbkdf2PasswordHash passwordHash) {
        this.key = key;
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
        try {
            List<String> roles = getRoles(user.getUsername());
            return Jwts.builder()
                    .setSubject(Constants.CLIENT)
                    .setIssuer(Constants.SERVER)
                    .claim(Constants.USERNAME, user.getUsername())
                    .claim(Constants.ROLES, roles)
                    .setExpiration(Date.from(LocalDateTime.now().plusSeconds(1).atZone(ZoneId.systemDefault()).toInstant()))
                    .signWith(key).compact();
        } catch (Exception e) {
            throw new NotFoundException(Constants.ERROR_WHILE_GENERATING_JWS);
        }
    }
}
