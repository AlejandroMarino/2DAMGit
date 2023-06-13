package dao;

import model.User;

import java.util.ArrayList;
import java.util.List;

public class UsuarioDao {
    private static List<User> users = new ArrayList<>();

    static {
        List<String> curioso = List.of("raton", "curioso");
        List<String> biologo = List.of("raton", "biologo");
        List<String> nivel1 = List.of("informe", "nivel1");
        List<String> nivel2 = List.of("informe", "nivel2");
        List<String> espia = List.of("informe", "espia");
        users.add(new User("uno", "uno", curioso));
        users.add(new User("dos", "dos", biologo));
        users.add(new User("tres", "tres", nivel1));
        users.add(new User("cuatro", "cuatro", nivel2));
        users.add(new User("cinco", "cinco", espia));
    }

    public User getUsuario(String nombre) {
        return users.stream().filter(user -> user.getName().equalsIgnoreCase(nombre)).findFirst().orElse(null);
    }
}
