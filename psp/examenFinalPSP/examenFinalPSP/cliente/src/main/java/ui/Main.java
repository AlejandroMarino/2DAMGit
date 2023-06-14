package ui;

import domain.models.Arma;
import domain.models.Usuario;
import io.reactivex.rxjava3.schedulers.Schedulers;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import services.ServicesArmas;
import services.ServicesPersonajes;
import services.ServicesSeries;
import services.ServicesUsuarios;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        SeContainerInitializer initializer = SeContainerInitializer.newInstance();
        final SeContainer container = initializer.initialize();
        ServicesArmas sA = container.select(ServicesArmas.class).get();
        ServicesPersonajes sP = container.select(ServicesPersonajes.class).get();
        ServicesSeries sS = container.select(ServicesSeries.class).get();
        ServicesUsuarios sU = container.select(ServicesUsuarios.class).get();

        Scanner sc = new Scanner(System.in);
        int option;
        do {
            System.out.println("""
                        \nWelcome
                    1. Register
                    2. Login
                    0. Exit""");
            option = sc.nextInt();
            sc.nextLine();
            switch (option) {
                case 1:
                    System.out.println("Introduce the username");
                    String username = sc.nextLine();
                    System.out.println("Introduce the role (el mio era armas)");
                    String role = sc.nextLine().trim().toUpperCase();
                    Usuario usuario = new Usuario(username, List.of(role));
                    sU.register(usuario).observeOn(Schedulers.single())
                            .blockingSubscribe(
                                    either -> {
                                        if (either.isLeft()) {
                                            System.out.println(either.getLeft());
                                        } else {
                                            System.out.println("Tu contraseña es: " + either.get().getPassword());
                                        }
                                    }
                            );
                    break;
                case 2:
                    login(sU, sc, sS, sA, sP);
                    break;
                case 0:
                    System.out.println("Bye");
                    break;
                default:
                    System.out.println("Introduce a valid option");
                    break;
            }
        } while (option != 0);
    }

    private static void login(ServicesUsuarios sU, Scanner sc, ServicesSeries sS, ServicesArmas sA, ServicesPersonajes sP) {
        System.out.println("\n\tLogin: ");
        System.out.println("Introduce the username");
        String username = sc.nextLine();
        System.out.println("Introduce the password");
        String password = sc.nextLine();
        Usuario usuario = new Usuario(username, password);
        sU.login(usuario).observeOn(Schedulers.single())
                .blockingSubscribe(
                        either -> {
                            if (either.isLeft()) {
                                System.out.println(either.getLeft());
                            } else {
                                System.out.println(either.get());
                                menu(sc, sS, sA, sP);
                            }
                        }
                );
    }

    private static void menu(Scanner sc, ServicesSeries sS, ServicesArmas sA, ServicesPersonajes sP) {
        int opt;
        do {
            System.out.println("""
                    \nWhat do you want to do?
                    1- Get todas las series
                    2- Get personajes que manejan un arma concreta con cierta habilidad
                    3- Add nuevo arma
                    4- Update arma
                    5- Delete arma sin relaciones
                    6- Delete arma con relaciones
                    0- Salir
                    """);
            opt = sc.nextInt();
            sc.nextLine();
            switch (opt) {
                case 1:
                    sS.getAllSeries().observeOn(Schedulers.single())
                            .blockingSubscribe(
                                    either -> {
                                        if (either.isLeft()) {
                                            System.out.println(either.getLeft());
                                        } else {
                                            System.out.println(either.get());
                                        }
                                    }
                            );
                    break;
                case 2:
                    sA.getAll().observeOn(Schedulers.single())
                            .blockingSubscribe(
                                    either -> {
                                        if (either.isLeft()) {
                                            System.out.println(either.getLeft());
                                        } else {
                                            System.out.println("Las armas son: ");
                                            System.out.println(either.get());
                                            System.out.println("Introduce el id del arma");
                                            int idArma = sc.nextInt();
                                            sc.nextLine();
                                            System.out.println("Introduce el numero de habilidad mínimo");
                                            int idHabilidad = sc.nextInt();
                                            sc.nextLine();
                                            sP.getPersonajesArmaHabilidad(idArma, idHabilidad).observeOn(Schedulers.single())
                                                    .blockingSubscribe(
                                                            ei -> {
                                                                if (ei.isLeft()) {
                                                                    System.out.println(ei.getLeft());
                                                                } else {
                                                                    System.out.println(ei.get());
                                                                }
                                                            }
                                                    );
                                        }
                                    }
                            );
                    break;
                case 3:
                    System.out.println("Introduce el id del arma");
                    int idArma = sc.nextInt();
                    sc.nextLine();
                    System.out.println("Introduce la descripcion del arma");
                    String descripcion = sc.nextLine();
                    System.out.println("Introduce el id de la serie");
                    int idSerie = sc.nextInt();
                    sc.nextLine();
                    sA.addArma(new Arma(idArma, descripcion, idSerie)).observeOn(Schedulers.single())
                            .blockingSubscribe(
                                    either -> {
                                        if (either.isLeft()) {
                                            System.out.println(either.getLeft());
                                        } else {
                                            System.out.println(either.get());
                                        }
                                    }
                            );
                    break;
                case 4:
                    System.out.println("Introduce el id del arma que quieres modificar");
                    int idArmaUpdate = sc.nextInt();
                    sc.nextLine();
                    System.out.println("Introduce la nueva descripcion del arma");
                    String descripcionUpdate = sc.nextLine();
                    sA.updateArma(new Arma(idArmaUpdate, descripcionUpdate)).observeOn(Schedulers.single())
                            .blockingSubscribe(
                                    either -> {
                                        if (either.isLeft()) {
                                            System.out.println(either.getLeft());
                                        } else {
                                            System.out.println(either.get());
                                        }
                                    }
                            );
                    break;
                case 5:
                    System.out.println("Introduce el id del arma que quieres borrar");
                    int idArmaDelete = sc.nextInt();
                    sc.nextLine();
                    sA.deleteSinRelaciones(idArmaDelete).observeOn(Schedulers.single())
                            .blockingSubscribe(
                                    either -> {
                                        if (either.isLeft()) {
                                            System.out.println(either.getLeft());
                                        } else {
                                            System.out.println(either.get());
                                        }
                                    }
                            );
                    break;
                case 6:
                    System.out.println("Introduce el id del arma que quieres borrar");
                    int idArmaDelete2 = sc.nextInt();
                    sc.nextLine();
                    sA.deleteConRelaciones(idArmaDelete2).observeOn(Schedulers.single())
                            .blockingSubscribe(
                                    either -> {
                                        if (either.isLeft()) {
                                            System.out.println(either.getLeft());
                                        } else {
                                            System.out.println(either.get());
                                        }
                                    }
                            );
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Introduce a valid option");
                    break;
            }
        } while (opt != 0);
    }
}
