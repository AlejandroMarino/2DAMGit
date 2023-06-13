package ui;

import dao.impl.DaoRatones;
import dao.impl.DaoUser;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import model.User;

public class Exercise2 {
    public static void main(String[] args) {
        SeContainerInitializer initializer = SeContainerInitializer.newInstance();
        final SeContainer container = initializer.initialize();
        DaoRatones dao = container.select(DaoRatones.class).get();
        Exercise2 example = new Exercise2();
        example.exercise(dao, container);
    }

    private void exercise(DaoRatones dao, SeContainer container) {
        Exercise1 ex = new Exercise1();
        ex.exercise(container);
        dao.getRatones().blockingSubscribe(resultado ->
                resultado.peek(list ->
                                list.forEach(raton -> System.out.println(raton.getNombre() + raton.getEdad())))
                        .peekLeft(System.out::println));
    }

}