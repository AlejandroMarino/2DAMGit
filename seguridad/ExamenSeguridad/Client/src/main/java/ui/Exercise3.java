package ui;

import dao.impl.DaoRatones;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import model.Raton;

public class Exercise3 {
    public static void main(String[] args) {
        SeContainerInitializer initializer = SeContainerInitializer.newInstance();
        final SeContainer container = initializer.initialize();
        DaoRatones dao = container.select(DaoRatones.class).get();
        Exercise3 example = new Exercise3();
        example.exercise(dao, container);
    }

    private void exercise(DaoRatones dao, SeContainer container) {
        Exercise1 ex = new Exercise1();
        ex.exercise(container);
        dao.addRaton(new Raton(1, "otro", 2)).blockingSubscribe(resultado ->
                resultado.peek(raton -> System.out.println(raton.getNombre() + raton.getEdad()))
                        .peekLeft(System.out::println));
    }

}