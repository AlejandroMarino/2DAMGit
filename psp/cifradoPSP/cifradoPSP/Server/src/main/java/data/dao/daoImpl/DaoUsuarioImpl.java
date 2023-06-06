package data.dao.daoImpl;

import common.Constants;
import common.HQLQueries;
import config.JPAUtil;
import data.dao.DaoUsuario;
import domain.model.errores.BaseDatosCaidaException;
import domain.model.errores.DataModificationException;
import domain.model.errores.NotFoundException;
import domain.models.Contrato;
import domain.models.Tipo;
import domain.models.Usuario;
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
    public Usuario get(String username) {
        Usuario u;
        em = jpaUtil.getEntityManager();
        try {
            u = em.createNamedQuery(HQLQueries.HQL_GET_USUARIO_BY_NAME, Usuario.class).setParameter("nombre", username).getSingleResult();
            if (u == null) {
                throw new NotFoundException("Usuario no encontrado");
            }
            return u;
        } catch (Exception e) {
            throw new BaseDatosCaidaException(Constants.DATABASE_ERROR);
        } finally {
            em.close();
        }
    }

    @Override
    public Usuario get(int id) {
        Usuario u;
        em = jpaUtil.getEntityManager();
        try {
            u = em.createNamedQuery(HQLQueries.HQL_GET_USUARIO_BY_ID, Usuario.class).setParameter("id", id).getSingleResult();
            if (u == null) {
                throw new NotFoundException("Usuario no encontrado");
            }
            return u;
        } catch (Exception e) {
            throw new BaseDatosCaidaException(Constants.DATABASE_ERROR);
        } finally {
            em.close();
        }
    }

    @Override
    public List<Usuario> getSicarios(Contrato contrato) {
        List<Usuario> l;
        em = jpaUtil.getEntityManager();
        try {
            l = em.createNamedQuery(HQLQueries.HQL_GET_SICARIOS_OF_CONTRATO, Usuario.class).setParameter("idContrato", contrato.getId()).getResultList();
            if (l == null || l.isEmpty()) {
                throw new NotFoundException("Sicarios no encontrados");
            }
            return l;
        } catch (Exception e) {
            throw new BaseDatosCaidaException(Constants.DATABASE_ERROR);
        } finally {
            em.close();
        }
    }

    @Override
    public List<Usuario> getSicariosFilterHabilidad(int habilidad) {
        List<Usuario> l;
        em = jpaUtil.getEntityManager();
        try {
            l = em.createNamedQuery(HQLQueries.HQL_GET_SICARIOS_FILTER_HABILIDAD, Usuario.class)
                    .setParameter("tipo", Tipo.SICARIO).setParameter("habilidad", habilidad).getResultList();
            if (l == null || l.isEmpty()) {
                throw new NotFoundException("Sicarios no encontrados");
            }
            return l;
        } catch (Exception e) {
            throw new BaseDatosCaidaException(Constants.DATABASE_ERROR);
        } finally {
            em.close();
        }
    }

    @Override
    public Usuario add(Usuario usuario) {
        em = jpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(usuario);
            em.getTransaction().commit();
            return usuario;
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new DataModificationException("Error al añadir usuario");
        } finally {
            em.close();
        }
    }
}
