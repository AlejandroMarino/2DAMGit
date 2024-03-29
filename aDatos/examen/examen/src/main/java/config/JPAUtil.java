package config;

import jakarta.ejb.Singleton;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

@Singleton
public class JPAUtil {

    private EntityManagerFactory emf;

    public JPAUtil() {
        emf=getEmf();
    }

    private EntityManagerFactory getEmf() {
        return Persistence.createEntityManagerFactory("examen");
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
}
