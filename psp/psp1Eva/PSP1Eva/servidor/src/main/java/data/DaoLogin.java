package data;

import domain.models.User;

import java.util.List;

public interface DaoLogin {

    User register(User user);

    User getUser(String username);

    List<String> getRoles(String username);

    boolean validate(String code);
}
