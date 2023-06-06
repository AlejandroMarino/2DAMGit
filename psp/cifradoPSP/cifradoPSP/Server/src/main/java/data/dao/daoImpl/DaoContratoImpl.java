package data.dao.daoImpl;

import common.Constants;
import common.HQLQueries;
import config.JPAUtil;
import data.dao.DaoContrato;
import domain.model.errores.BaseDatosCaidaException;
import domain.model.errores.DataModificationException;
import domain.model.errores.NotFoundException;
import domain.models.Contrato;
import domain.models.Estado;
import domain.models.Usuario;
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
    public List<Contrato> getAllOfContratista(Usuario usuario) {
        List<Contrato> l;
        em = jpaUtil.getEntityManager();
        try {
            l = em.createNamedQuery(HQLQueries.HQL_GET_CONTRATOS_OF_USUARIO, Contrato.class)
                    .setParameter("idUsuario", usuario.getId()).getResultList();
            if (l == null || l.isEmpty()) {
                throw new NotFoundException("Contratos no encontrados");
            }
            return l;
        } catch (Exception e) {
            throw new BaseDatosCaidaException(Constants.DATABASE_ERROR);
        } finally {
            em.close();
        }
    }

    @Override
    public List<Contrato> getAllOfSicario(Usuario usuario) {
        List<Contrato> l;
        em = jpaUtil.getEntityManager();
        try {
            l = em.createNamedQuery(HQLQueries.HQL_GET_CONTRATOS_OF_SICARIO, Contrato.class)
                    .setParameter("idSicario", usuario.getId()).getResultList();
            if (l == null || l.isEmpty()) {
                throw new NotFoundException("Contratos no encontrados");
            }
            return l;
        } catch (Exception e) {
            throw new BaseDatosCaidaException(Constants.DATABASE_ERROR);
        } finally {
            em.close();
        }
    }

    @Override
    public List<Contrato> getAllOfSicarioFilterEstado(Usuario usuario, Estado estado) {
        List<Contrato> l;
        em = jpaUtil.getEntityManager();
        try {
            l = em.createNamedQuery(HQLQueries.HQL_GET_CONTRATOS_OF_SICARIO_FILTER_ESTADO, Contrato.class)
                    .setParameter("idSicario", usuario.getId()).setParameter("estado", estado).getResultList();
            if (l == null || l.isEmpty()) {
                throw new NotFoundException("Contratos no encontrados");
            }
            return l;
        } catch (Exception e) {
            throw new BaseDatosCaidaException(Constants.DATABASE_ERROR);
        } finally {
            em.close();
        }
    }

    @Override
    public Contrato get(int id) {
        Contrato c;
        em = jpaUtil.getEntityManager();
        try {
            c = em.createNamedQuery(HQLQueries.HQL_GET_CONTRATO_BY_ID, Contrato.class).setParameter("id", id).getSingleResult();
            if (c == null) {
                throw new NotFoundException("Contrato no encontrado");
            }
            return c;
        } catch (Exception e) {
            throw new BaseDatosCaidaException(Constants.DATABASE_ERROR);
        } finally {
            em.close();
        }
    }

    @Override
    public Contrato add(Contrato contrato) {
        em = jpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(contrato);
            em.getTransaction().commit();
            return contrato;
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new DataModificationException("Error al añadir contrato");
        } finally {
            em.close();
        }
    }

    @Override
    public boolean updateable(Contrato contrato) {
        List<Estado> l;
        em = jpaUtil.getEntityManager();
        try {
            l = em.createNamedQuery(HQLQueries.HQL_GET_ESTADOS_CONTRATO, Estado.class)
                    .setParameter("idContrato", contrato.getId()).getResultList();
            if (l == null || l.isEmpty()) {
                throw new NotFoundException("Contrato no encontrado");
            } else {
                return l.stream().allMatch(estado -> estado.equals(Estado.PENDIENTE));
            }

        } catch (Exception e) {
            throw new BaseDatosCaidaException(Constants.DATABASE_ERROR);
        } finally {
            em.close();
        }
    }

    @Override
    public void update(Contrato contrato) {
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
