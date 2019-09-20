package biz.oneilindustries.dao;

import biz.oneilindustries.config.HibernateConfig;
import biz.oneilindustries.hibrenate.entity.User;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class UserDAO {

    private SessionFactory sessionFactory;
    private Session session;
    private Transaction transaction;

    public UserDAO() {
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

    public List getUsers() {
        openSession();

        Query query = session.createQuery("from User");

        return query.getResultList();
    }

    public User getUserBySteamID(String userSteamID) {
        openSession();

        Query query= session.
            createQuery("from User where steamID=:userSteamID");
        query.setParameter("userSteamID", userSteamID);
        return (User) query.uniqueResult();
    }

    public User getUserByDiscordID(String userDiscordID) {
        openSession();

        Query query= session.
            createQuery("from User where discordID=:userDiscordID");
        query.setParameter("userDiscordID", userDiscordID);
        return (User) query.uniqueResult();
    }

    public void saveUser(User user) {
        openSession();

        session.saveOrUpdate(user);
        transaction.commit();
    }

    public void deleteUser(User user) {
        openSession();

        session.delete(user);

        transaction.commit();
    }
}
