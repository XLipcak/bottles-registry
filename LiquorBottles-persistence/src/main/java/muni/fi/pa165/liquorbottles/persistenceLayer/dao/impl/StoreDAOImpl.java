package muni.fi.pa165.liquorbottles.persistenceLayer.dao.impl;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import muni.fi.pa165.liquorbottles.persistenceLayer.dao.StoreDAO;
import muni.fi.pa165.liquorbottles.persistenceLayer.dao.UserDAO;
import muni.fi.pa165.liquorbottles.persistenceLayer.entities.Store;
import org.springframework.beans.factory.annotation.Required;

/**
 *
 * @author Matúš Novák, Masaryk University
 */
public class StoreDAOImpl implements StoreDAO {

    @PersistenceContext
    EntityManager em;

    UserDAO userDAO;

    public StoreDAOImpl() {
    }

    public StoreDAOImpl(EntityManager em) {
        this.em = em;
        userDAO = new UserDAOImpl(em);
    }

    @Override
    public List<Store> findAll() {

        try {
            TypedQuery<Store> allStoreQuerry = em.createQuery("SELECT s FROM Store s", Store.class);
            List<Store> allStore = allStoreQuerry.getResultList();

            return allStore;
        } catch (Exception ex) {
            throw new PersistenceException("Transaction failed. \n" + ex.getMessage(), ex);
        }
    }

    @Override
    public Store findById(long id) {

        try {
            Store store = em.find(Store.class, id);

            return store;
        } catch (Exception ex) {
            throw new PersistenceException("Transaction failed. \n" + ex.getMessage(), ex);
        }
    }

    @Override
    public Store findByAddress(String address) {

        try {
            TypedQuery<Store> policeByAddress = em.createQuery("SELECT s FROM Store s "
                    + "WHERE s.address='" + address + "'", Store.class);
            Store store = policeByAddress.getSingleResult();

            return store;
        } catch (Exception ex) {
            throw new PersistenceException("Transaction failed. \n" + ex.getMessage(), ex);
        }
    }

    @Override
    public void insertStore(Store store) {
        userDAO.insertUser(store);
    }

    @Override
    public void updateStore(Store store) {
        userDAO.updateUser(store);
    }

    @Override
    public void deleteStore(Store store) {
        userDAO.deleteUser(store);
    }

    @Required
    public void setEm(EntityManager em) {
        this.em = em;
    }

    @Required

    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

}
