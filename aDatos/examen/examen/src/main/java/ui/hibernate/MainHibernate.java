package ui.hibernate;


import domain.model.modelHibernate.ObjetoHibernate;
import domain.model.modelHibernate.PermisosHibernate;
import domain.model.modelHibernate.PermisosObjetosHibernate;
import domain.model.modelHibernate.UsuarioHibernate;
import domain.services.servicesHibernate.ServicesPermisos;
import domain.services.servicesHibernate.ServicesPermisosObjeto;
import domain.services.servicesMongo.ServicesMongo;
import io.vavr.control.Either;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;


import java.util.Scanner;
import java.util.Set;

public class MainHibernate {
    public static void main(String[] args) {
        SeContainerInitializer initializer = SeContainerInitializer.newInstance();
        final SeContainer container = initializer.initialize();
        ServicesMongo svl = container.select(ServicesMongo.class).get();
        ServicesPermisosObjeto sPO = container.select(ServicesPermisosObjeto.class).get();
        ServicesPermisos sP = container.select(ServicesPermisos.class).get();

        Scanner sc = new Scanner(System.in);
        int option;
        do {
            System.out.println("""
                    \nWhat you want to do?
                    \t1- Add permission to user on object
                    \t2- Get permissions of User on Object 
                    \t0- Exit""");
            option = sc.nextInt();
            sc.nextLine();
            switch (option) {
                case 1:
                    addPermissionUserObject(sPO, sc);
                    break;
                case 2:
                    getPermissionUserObject(sP, sc);
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

    private static void getPermissionUserObject(ServicesPermisos sP, Scanner sc) {
        System.out.println("introduce the id of the user");
        int idU = sc.nextInt();
        sc.nextLine();
        UsuarioHibernate u = new UsuarioHibernate(idU);
        System.out.println("introduce the id of the object");
        int idO = sc.nextInt();
        sc.nextLine();
        ObjetoHibernate o = new ObjetoHibernate(idO);
        Either<String, Set<PermisosHibernate>> r = sP.getPermisosUsuarioObjeto(new PermisosObjetosHibernate(u,o));
        if (r.isLeft()) {
            System.out.println(r.getLeft());
        } else {
            System.out.println(r.get());
        }
    }

    private static void addPermissionUserObject(ServicesPermisosObjeto sPO, Scanner sc) {
        System.out.println("introduce the id of the user");
        int idU = sc.nextInt();
        sc.nextLine();
        UsuarioHibernate u = new UsuarioHibernate(idU);
        System.out.println("introduce the id of the object");
        int idO = sc.nextInt();
        sc.nextLine();
        ObjetoHibernate o = new ObjetoHibernate(idO);
        System.out.println("introduce the id of the permission");
        int idP = sc.nextInt();
        sc.nextLine();
        PermisosHibernate p = new PermisosHibernate(idP);
        System.out.println(sPO.addPermiso(new PermisosObjetosHibernate(u,o,p)));
    }
}