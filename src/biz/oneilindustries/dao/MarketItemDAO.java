package biz.oneilindustries.dao;

import biz.oneilindustries.config.HibernateConfig;
import biz.oneilindustries.hibrenate.entity.MarketItem;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class MarketItemDAO {

    private SessionFactory sessionFactory;
    private Session session;
    private Transaction transaction;

    public MarketItemDAO() {
        sessionFactory = HibernateConfig.getSessionFactory();
        session = sessionFactory.getCurrentSession();
        transaction = session.beginTransaction();
    }

    private void openSession() {
        if (!session.isOpen()) {
            session = sessionFactory.getCurrentSession();
            transaction = session.beginTransaction();
        }
    }

    public void close() {
        session.close();
    }

    public List<MarketItem> getItems() {
        openSession();

        Query query = session.createQuery("from MarketItem");

        return query.getResultList();
    }

    public List<MarketItem> getItemsByUser(String steamID) {
        openSession();

        Query query = session.createQuery("from MarketItem where ownerID=:steamID");
        query.setParameter("steamID", steamID);

        return query.getResultList();
    }

    public List<MarketItem> getItemsByName(String name) {
        openSession();

        Query query = session.createQuery("from MarketItem where item=:name");
        query.setParameter("item", name);

        return query.getResultList();
    }

    public MarketItem getItem(int id) {
        openSession();

        return session.get(MarketItem.class, id);
    }

    public void saveItem(MarketItem item) {
        openSession();

        session.saveOrUpdate(item);
        transaction.commit();
    }
}