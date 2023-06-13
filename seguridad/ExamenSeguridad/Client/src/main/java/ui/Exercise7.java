package ui;

import dao.impl.DaoRatones;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import model.exceptions.BadRequestException;

public class Exercise7 {
    public static void main(String[] args) {
        SeContainerInitializer initializer = SeContainerInitializer.newInstance();
        final SeContainer container = initializer.initialize();
        DaoRatones dao = container.select(DaoRatones.class).get();
        Exercise7 example = new Exercise7();
        example.exercise(dao, container);
    }

    private void exercise(DaoRatones dao, SeContainer container) {
        Exercise1 ex = new Exercise1();
        ex.exercise(container);
        try {
            Thread.sleep(11000);
            dao.getRatones().blockingSubscribe(resultado ->
                    resultado.peek(list ->
                                    list.forEach(raton -> System.out.println(raton.getNombre() + raton.getEdad())))
                            .peekLeft(System.out::println));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (BadRequestException e){
            System.out.println(e.getMessage());
        }
    }

}