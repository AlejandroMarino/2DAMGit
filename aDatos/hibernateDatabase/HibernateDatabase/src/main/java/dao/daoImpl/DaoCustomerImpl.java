package dao.daoImpl;

import config.JPAUtil;
import dao.DaoCustomer;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

public class DaoCustomerImpl implements DaoCustomer {

    private JPAUtil jpaUtil;
    private EntityManager em;

    @Inject
    public DaoCustomerImpl(JPAUtil jpaUtil) {
        this.jpaUtil = jpaUtil;
    }

    public
}
