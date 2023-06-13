package ui;

import dao.impl.DaoUser;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import model.User;

public class Exercise1 {
    public static void main(String[] args) {
        SeContainerInitializer initializer = SeContainerInitializer.newInstance();
        final SeContainer container = initializer.initialize();
        Exercise1 example = new Exercise1();
        example.exercise(container);
    }

    public void exercise(SeContainer container) {
        DaoUser dao = container.select(DaoUser.class).get();
        User user = new User("uno", "dos");
        dao.getValidatedUser(user).blockingSubscribe(resultado ->
                resultado.peek(user1 -> System.out.println("USUARIO: " + user1.getName()))
                        .peekLeft(System.out::println));
    }

}