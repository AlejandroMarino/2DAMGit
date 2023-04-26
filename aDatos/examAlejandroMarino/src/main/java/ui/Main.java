package ui;

import domain.modelJDBCSpring.Permiso;
import domain.modelJDBCSpring.Usuario;
import io.vavr.control.Either;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import services.ServicesPermissions;
import services.ServicesUser;
import services.ServicesXml;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        SeContainerInitializer initializer = SeContainerInitializer.newInstance();
        final SeContainer container = initializer.initialize();
        ServicesUser sU = container.select(ServicesUser.class).get();
        ServicesPermissions sP = container.select(ServicesPermissions.class).get();
        ServicesXml sX = container.select(ServicesXml.class).get();

        Scanner sc = new Scanner(System.in);
        int option;
        do {
            System.out.println("\nWhat you want to do?" +
                    "\n\t1- Add write permissions to user 2 on object 2" +
                    "\n\t2- Remove location 3" +
                    "\n\t3- Get permissions of user 5 on object 32" +
                    "\n\t4- Modify the name od user 2 with your own name" +
                    "\n\t5- Calculate the average number of users.xml who have permissions per object" +
                    "\n\t6- Backup the users.xml table including permissions the have per object" +
                    "\n\t0- Exit");
            option = sc.nextInt();
            sc.nextLine();
            switch (option) {
                case 1:
                    Either<String, Void> add = sP.addPermission();
                    if (add.isLeft()) {
                        System.out.println(add.getLeft());
                    } else {
                        System.out.println("permission added");
                    }
                    break;
                case 2:

                    break;
                case 3:
                    Either<String, List<Permiso>> permisos = sP.getPermisions();
                    if (permisos.isLeft()) {
                        System.out.println(permisos.getLeft());
                    }else {
                        System.out.println(permisos.get());
                    }
                    break;
                case 4:
                    System.out.println("What is your name?");
                    String name = sc.nextLine();
                    Either<String, Void> r = sU.updateUserName(new Usuario(name));
                    if (r.isLeft()){
                        System.out.println(r.getLeft());
                    } else {
                        System.out.println("username updated");
                    }
                    break;
                case 5:
                    break;
                case 6:
                    Either<String, Void> result = sX.generateXml();
                    if (result.isLeft()){
                        System.out.println(result.getLeft());
                    } else {
                        System.out.println("xml saved");
                    }
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
}
