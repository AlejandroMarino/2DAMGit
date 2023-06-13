package data.dao.daoImpl;

import common.Constants;
import common.HQLQueries;
import config.JPAUtil;
import data.dao.DaoUsuario;
import data.model.ContratoEntity;
import data.model.UsuarioEntity;
import domain.model.errores.BaseDatosCaidaException;
import domain.model.errores.DataModificationException;
import domain.model.errores.NotFoundException;
import domain.models.Tipo;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import java.util.List;

public class DaoUsuarioImpl implements DaoUsuario {

    private final JPAUtil jpaUtil;
    private EntityManager em;

    @Inject
    public DaoUsuarioImpl(JPAUtil jpaUtil) {
        this.jpaUtil = jpaUtil;
    }

    @Override
    public UsuarioEntity get(String username) {
        UsuarioEntity u;
        em = jpaUtil.getEntityManager();
        try {
            u = em.createNamedQuery(HQLQueries.HQL_GET_USUARIO_BY_NAME, UsuarioEntity.class).setParameter("nombre", username).getSingleResult();
        } catch (Exception e) {
            throw new BaseDatosCaidaException(Constants.DATABASE_ERROR);
        } finally {
            em.close();
        }
        if (u == null) {
            throw new NotFoundException("Usuario no encontrado");
        }
        return u;
    }

    
    @Override
    public UsuarioEntity get(int id) {
        UsuarioEntity u;
        em = jpaUtil.getEntityManager();
        try {
            u = em.createNamedQuery(HQLQueries.HQL_GET_USUARIO_BY_ID, UsuarioEntity.class).setParameter("id", id).getSingleResult();
        } catch (Exception e) {
            throw new BaseDatosCaidaException(Constants.DATABASE_ERROR);
        } finally {
            em.close();
        }
        if (u == null) {
            throw new NotFoundException("Usuario no encontrado");
        }
        return u;
    }

    @Override
    public List<UsuarioEntity> getSicarios(int idContrato) {
        List<UsuarioEntity> l;
        em = jpaUtil.getEntityManager();
        try {
            l = em.createNamedQuery(HQLQueries.HQL_GET_SICARIOS_OF_CONTRATO, UsuarioEntity.class).setParameter("idContrato", idContrato).getResultList();
        } catch (Exception e) {
            throw new BaseDatosCaidaException(Constants.DATABASE_ERROR);
        } finally {
            em.close();
        }
        if (l == null || l.isEmpty()) {
            throw new NotFoundException("Sicarios no encontrados");
        }
        return l;
    }

    @Override
    public List<UsuarioEntity> getSicariosFilterHabilidad(int habilidad) {
        List<UsuarioEntity> l;
        em = jpaUtil.getEntityManager();
        try {
            l = em.createNamedQuery(HQLQueries.HQL_GET_SICARIOS_FILTER_HABILIDAD, UsuarioEntity.class)
                    .setParameter("tipo", Tipo.SICARIO).setParameter("habilidad", habilidad).getResultList();
        } catch (Exception e) {
            throw new BaseDatosCaidaException(Constants.DATABASE_ERROR);
        } finally {
            em.close();
        }
        if (l == null || l.isEmpty()) {
            throw new NotFoundException("Sicarios no encontrados");
        }
        return l;
    }

    @Override
    public UsuarioEntity add(UsuarioEntity usuario) {
        em = jpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(usuario);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new DataModificationException("Error al añadir usuario");
        } finally {
            em.close();
        }
        return usuario;
    }
}
