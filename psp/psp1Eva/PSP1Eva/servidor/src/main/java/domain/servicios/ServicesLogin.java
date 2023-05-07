package domain.servicios;

import domain.models.User;

import java.util.List;

public interface ServicesLogin {

    User register(User user);

    User validationUser(String username, char[] password);

    List<String> getRoles(String username);

    boolean validate(String code);

    boolean activated(String username);

    String generateJWS(User user);
}
