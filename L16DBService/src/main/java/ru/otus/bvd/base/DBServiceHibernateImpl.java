package ru.otus.bvd.base;

import java.io.File;
import java.sql.SQLException;
import java.util.List;
import java.util.function.Function;

import org.h2.tools.Server;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import ru.otus.bvd.dao.UserDataSetDaoHibernate;
import ru.otus.bvd.dataset.AddressDataSet;
import ru.otus.bvd.dataset.PhoneDataSet;
import ru.otus.bvd.dataset.UserDataSet;

public class DBServiceHibernateImpl implements DBService {
    private final SessionFactory sessionFactory;

    public DBServiceHibernateImpl() {
        Configuration configuration = new Configuration();
        
        configuration.addAnnotatedClass(AddressDataSet.class);
        configuration.addAnnotatedClass(PhoneDataSet.class);
        configuration.addAnnotatedClass(UserDataSet.class);

        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        configuration.setProperty("hibernate.connection.driver_class", "org.h2.Driver");
        File file = new File("");       
        configuration.setProperty("hibernate.connection.url", "jdbc:h2:tcp://localhost/"+file.getAbsolutePath().replace('\\', '/')+"/baza;DB_CLOSE_DELAY=-1;AUTO_SERVER=TRUE");
        configuration.setProperty("hibernate.connection.username", "otus");
        configuration.setProperty("hibernate.connection.password", "otus");
        configuration.setProperty("hibernate.show_sql", "true");
        configuration.setProperty("hibernate.hbm2ddl.auto", "create");
        configuration.setProperty("hibernate.connection.useSSL", "false");
        configuration.setProperty("hibernate.enable_lazy_load_no_trans", "true");
        configuration.setProperty("hibernate.jdbc.time_zone", "UTC");

        sessionFactory = createSessionFactory(configuration);                
    }
        
    @Override
    public String getLocalStatus() {
        return runInSession(session -> {
            return session.getTransaction().getStatus().name();
        });
    }

    @Override
    public void save(UserDataSet dataSet) {
        try (Session session = sessionFactory.openSession()) {
            UserDataSetDaoHibernate dao = new UserDataSetDaoHibernate(session);
            dao.save(dataSet);
        }
    }

    @Override
    public UserDataSet read(long id) {
        return runInSession(session -> {
            UserDataSetDaoHibernate dao = new UserDataSetDaoHibernate(session);
            return dao.read(id);
        });
    }

    @Override
    public UserDataSet readByName(String name) {
        return runInSession(session -> {
            UserDataSetDaoHibernate dao = new UserDataSetDaoHibernate(session);
            return dao.readByName(name);
        });
    }

    @Override
    public List<UserDataSet> readAll() {
        return runInSession(session -> {
            UserDataSetDaoHibernate dao = new UserDataSetDaoHibernate(session);
            return dao.readAll();
        });
    }

    public void shutdown() {
        sessionFactory.close();
    }
   
    private static SessionFactory createSessionFactory(Configuration configuration) {
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
        builder.applySettings(configuration.getProperties());
        ServiceRegistry serviceRegistry = builder.build();
        return configuration.buildSessionFactory(serviceRegistry);
    }
    
    
    private <R> R runInSession(Function<Session, R> function) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            R result = function.apply(session);
            transaction.commit();
            return result;
        }
    }
    
}
