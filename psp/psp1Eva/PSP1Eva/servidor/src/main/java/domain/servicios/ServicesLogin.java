package domain.servicios;

import domain.models.User;

import java.util.Set;

public interface ServicesLogin {

    User register(User user);

    User validationUser(String username, char[] password);

    Set<String> getRoles(String username);

    boolean validate(String code);

    boolean activated(String username);

    String generateJWS(User user);
}
