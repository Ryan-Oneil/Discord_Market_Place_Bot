package biz.oneilindustries.hibrenate;


import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateConfig {
    private static Configuration configuration;

    static {
        configuration = new Configuration().configure();
        configuration.addAnnotatedClass(biz.oneilindustries.hibrenate.entity.User.class);
        configuration.addAnnotatedClass(biz.oneilindustries.hibrenate.entity.MarketItem.class);
    }

    public static SessionFactory getSessionFactory() {
        return configuration.buildSessionFactory();
    }

    private HibernateConfig() {
    }
}
