package data.dao.daoImpl;

import common.Constants;
import common.HQLQueries;
import config.JPAUtil;
import data.dao.DaoSicariosContratos;
import domain.model.errores.BaseDatosCaidaException;
import domain.model.errores.NotFoundException;
import domain.models.SicarioContrato;
import domain.models.Estado;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

public class DaoSicariosContratosImpl implements DaoSicariosContratos {

    private final JPAUtil jpaUtil;
    private EntityManager em;

    @Inject
    public DaoSicariosContratosImpl(JPAUtil jpaUtil) {
        this.jpaUtil = jpaUtil;
    }

    @Override
    public Estado getEstado(SicarioContrato sicarioContrato) {
        Estado e;
        em = jpaUtil.getEntityManager();
        try {
            e = em.createNamedQuery(HQLQueries.HQL_GET_ESTADO_SICARIO_CONTRATO, Estado.class)
                    .setParameter("idSicario", sicarioContrato.getUsuario().getId())
                    .setParameter("idContrato", sicarioContrato.getContrato().getId()).getSingleResult();
            if (e == null) {
                throw new NotFoundException("Estado no encontrado");
            }
            return e;
        } catch (Exception ex) {
            throw new BaseDatosCaidaException(Constants.DATABASE_ERROR);
        } finally {
            em.close();
        }
    }

    @Override
    public SicarioContrato add(SicarioContrato sicarioContrato) {
        em = jpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(sicarioContrato);
            em.getTransaction().commit();
            return sicarioContrato;
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new BaseDatosCaidaException(Constants.DATABASE_ERROR);
        } finally {
            em.close();
        }
    }

    @Override
    public SicarioContrato update(SicarioContrato sicarioContrato) {
        em = jpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(sicarioContrato);
            em.getTransaction().commit();
            return sicarioContrato;
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new BaseDatosCaidaException(Constants.DATABASE_ERROR);
        } finally {
            em.close();
        }
    }
}
