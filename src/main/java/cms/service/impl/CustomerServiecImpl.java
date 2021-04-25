package cms.service.impl;


import cms.model.Customer;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class CustomerServiecImpl{

    private static SessionFactory sessionFactory;
    private static EntityManager entityManager;

    static {
        try {
            sessionFactory = new Configuration()
                    .configure("hibernate.conf.xml")
                    .buildSessionFactory();
            entityManager = sessionFactory.createEntityManager();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }


    public List<Customer> findAll() {
        String queryStr = "SELECT c FROM Customer AS c";
        TypedQuery<Customer> query = entityManager.createQuery(queryStr, Customer.class);
        return query.getResultList();
    }

    public Customer findOne(Long id) {
        String queryStr = "SELECT c FROM Customer AS c WHERE c.id = :id";
        Customer customer = entityManager.createQuery(queryStr,Customer.class).setParameter("id", id).getSingleResult();
        return customer;
    }

    public Customer save(Customer customer) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            Customer origin = findOne(customer.getId());
            origin.setLastName(customer.getLastName());
            origin.setFirstName(customer.getFirstName());
            session.saveOrUpdate(origin);
            transaction.commit();
            return origin;
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return null;
    }

}
