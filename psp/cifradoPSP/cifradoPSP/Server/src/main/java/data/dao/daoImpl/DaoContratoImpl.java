package data.dao.daoImpl;

import common.Constants;
import common.HQLQueries;
import config.JPAUtil;
import data.dao.DaoContrato;
import data.model.ContratoEntity;
import data.model.UsuarioEntity;
import domain.model.errores.BaseDatosCaidaException;
import domain.model.errores.DataModificationException;
import domain.model.errores.NotFoundException;
import domain.models.Estado;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import java.util.List;

public class DaoContratoImpl implements DaoContrato {

    private final JPAUtil jpaUtil;
    private EntityManager em;

    @Inject
    public DaoContratoImpl(JPAUtil jpaUtil) {
        this.jpaUtil = jpaUtil;
    }

    @Override
    public List<ContratoEntity> getAllOfContratista(UsuarioEntity usuario) {
        List<ContratoEntity> l;
        em = jpaUtil.getEntityManager();
        try {
            l = em.createNamedQuery(HQLQueries.HQL_GET_CONTRATOS_OF_USUARIO, ContratoEntity.class)
                    .setParameter("idUsuario", usuario.getId()).getResultList();
        } catch (Exception e) {
            throw new BaseDatosCaidaException(Constants.DATABASE_ERROR);
        } finally {
            em.close();
        }
        if (l == null || l.isEmpty()) {
            throw new NotFoundException("Contratos no encontrados");
        }
        return l;
    }

    @Override
    public List<ContratoEntity> getAllOfSicario(UsuarioEntity usuario) {
        List<ContratoEntity> l;
        em = jpaUtil.getEntityManager();
        try {
            l = em.createNamedQuery(HQLQueries.HQL_GET_CONTRATOS_OF_SICARIO, ContratoEntity.class)
                    .setParameter("idSicario", usuario.getId()).getResultList();
        } catch (Exception e) {
            throw new BaseDatosCaidaException(Constants.DATABASE_ERROR);
        } finally {
            em.close();
        }
        if (l == null || l.isEmpty()) {
            throw new NotFoundException("Contratos no encontrados");
        }
        return l;
    }

    @Override
    public List<ContratoEntity> getAllOfSicarioFilterEstado(UsuarioEntity usuario, Estado estado) {
        List<ContratoEntity> l;
        em = jpaUtil.getEntityManager();
        try {
            l = em.createNamedQuery(HQLQueries.HQL_GET_CONTRATOS_OF_SICARIO_FILTER_ESTADO, ContratoEntity.class)
                    .setParameter("idSicario", usuario.getId()).setParameter("estado", estado).getResultList();
        } catch (Exception e) {
            throw new BaseDatosCaidaException(Constants.DATABASE_ERROR);
        } finally {
            em.close();
        }
        if (l == null || l.isEmpty()) {
            throw new NotFoundException("Contratos no encontrados");
        }
        return l;
    }

    @Override
    public ContratoEntity get(int id) {
        ContratoEntity c;
        em = jpaUtil.getEntityManager();
        try {
            c = em.createNamedQuery(HQLQueries.HQL_GET_CONTRATO_BY_ID, ContratoEntity.class).setParameter("id", id).getSingleResult();
        } catch (Exception e) {
            throw new BaseDatosCaidaException(Constants.DATABASE_ERROR);
        } finally {
            em.close();
        }
        if (c == null) {
            throw new NotFoundException("Contrato no encontrado");
        }
        return c;
    }

    @Override
    public ContratoEntity add(ContratoEntity contrato) {
        em = jpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(contrato);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new DataModificationException("Error al añadir contrato");
        } finally {
            em.close();
        }
        return contrato;
    }

    @Override
    public boolean updateable(ContratoEntity contrato) {
        List<Estado> l;
        em = jpaUtil.getEntityManager();
        try {
            l = em.createNamedQuery(HQLQueries.HQL_GET_ESTADOS_CONTRATO, Estado.class)
                    .setParameter("idContrato", contrato.getId()).getResultList();
        } catch (Exception e) {
            throw new BaseDatosCaidaException(Constants.DATABASE_ERROR);
        } finally {
            em.close();
        }
        if (l == null || l.isEmpty()) {
            throw new NotFoundException("Contrato no encontrado");
        } else {
            return l.stream().allMatch(estado -> estado.equals(Estado.PENDIENTE));
        }
    }

    @Override
    public void update(ContratoEntity contrato) {
        em = jpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(contrato);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new DataModificationException("Error al actualizar contrato");
        } finally {
            em.close();
        }
    }
}
