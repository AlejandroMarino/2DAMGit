package ui;

import dao.impl.DaoInformes;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import model.Informe;

import java.time.LocalDate;
import java.util.List;

public class Exercise5 {
    public static void main(String[] args) {
        SeContainerInitializer initializer = SeContainerInitializer.newInstance();
        final SeContainer container = initializer.initialize();
        DaoInformes dao = container.select(DaoInformes.class).get();
        Exercise5 example = new Exercise5();
        example.exercise(dao, container);
    }

    private void exercise(DaoInformes dao, SeContainer container) {
        Exercise1 ex = new Exercise1();
        ex.exercise(container);
        dao.getInforme(5).blockingSubscribe(resultado ->
                resultado.peek(informe -> System.out.println(informe.getNombre()))
                        .peekLeft(System.out::println));
    }

}