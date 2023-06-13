package data.dao.daoImpl;

import common.Constants;
import common.HQLQueries;
import config.JPAUtil;
import data.dao.DaoSicariosContratos;
import data.model.SicarioContratoEntity;
import domain.model.errores.BaseDatosCaidaException;
import domain.model.errores.NotFoundException;
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
    public SicarioContratoEntity get(SicarioContratoEntity sicarioContrato) {
        SicarioContratoEntity sC;
        em = jpaUtil.getEntityManager();
        try {
            sC = em.createNamedQuery(HQLQueries.HQL_GET_SICARIO_CONTRATO, SicarioContratoEntity.class)
                    .setParameter("idSicario", sicarioContrato.getSicario().getId())
                    .setParameter("idContrato", sicarioContrato.getContrato().getId()).getSingleResult();
        } catch (Exception ex) {
            throw new BaseDatosCaidaException(Constants.DATABASE_ERROR);
        } finally {
            em.close();
        }
        if (sC == null) {
            throw new NotFoundException("SicarioContrato no encontrado");
        }
        return sC;
    }

    @Override
    public SicarioContratoEntity add(SicarioContratoEntity sicarioContrato) {
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
    public SicarioContratoEntity update(SicarioContratoEntity sicarioContrato) {
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
