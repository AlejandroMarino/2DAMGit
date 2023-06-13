package ui;

import dao.impl.DaoInformes;
import dao.impl.DaoRatones;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;

public class Exercise4 {
    public static void main(String[] args) {
        SeContainerInitializer initializer = SeContainerInitializer.newInstance();
        final SeContainer container = initializer.initialize();
        DaoInformes dao = container.select(DaoInformes.class).get();
        Exercise4 example = new Exercise4();
        example.exercise(dao, container);
    }

    private void exercise(DaoInformes dao, SeContainer container) {
        Exercise1 ex = new Exercise1();
        ex.exercise(container);
        dao.getInformes().blockingSubscribe(resultado ->
                resultado.peek(list ->
                                list.forEach(informe -> System.out.println(informe.getNombre())))
                        .peekLeft(System.out::println));
    }

}