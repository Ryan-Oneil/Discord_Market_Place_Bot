package biz.oneilindustries.dao;

import biz.oneilindustries.hibrenate.HibernateConfig;
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
        session = sessionFactory.openSession();
    }

    private void openSession() {
        if (sessionFactory.isClosed() || !session.isOpen()) {
            sessionFactory = HibernateConfig.getSessionFactory();
            session = sessionFactory.openSession();
        }
    }

    public List getUsers() {

        Query query = session.createQuery("from User");

        return query.getResultList();
    }

    public User getUserBySteamID(String userSteamID) {
        Query query= session.
            createQuery("from User where steamID=:userSteamID");
        query.setParameter("userSteamID", userSteamID);
        return (User) query.uniqueResult();
    }

    public User getUserByDiscordID(String userDiscordID) {
        Query query= session.
            createQuery("from User where discordID=:userDiscordID");
        query.setParameter("userDiscordID", userDiscordID);
        return (User) query.uniqueResult();
    }

    public void saveUser(User user) {
        openSession();

        transaction = session.beginTransaction();

        session.saveOrUpdate(user);
        commit();
    }

    public void deleteUser(User user) {
        openSession();

        transaction = session.beginTransaction();

        session.delete(user);

        commit();
    }

    private void commit() {
        transaction.commit();
        close();
    }

    public void close() {
        session.close();
    }

}
