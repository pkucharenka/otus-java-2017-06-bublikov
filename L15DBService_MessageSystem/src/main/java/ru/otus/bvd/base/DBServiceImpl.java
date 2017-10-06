package ru.otus.bvd.base;

import static java.util.logging.Level.INFO;
import static java.util.logging.Level.SEVERE;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import ru.otus.bvd.app.MessageSystemContext;
import ru.otus.bvd.cache.CacheElement;
import ru.otus.bvd.cache.CacheEngine;
import ru.otus.bvd.dao.UserDataSetDao;
import ru.otus.bvd.dataset.UserDataSet;
import ru.otus.bvd.db.Database;
import ru.otus.bvd.messagesystem.Address;
import ru.otus.bvd.messagesystem.Addressee;

public class DBServiceImpl implements DBService, ApplicationContextAware, Addressee {
    private static final Logger log = Logger.getLogger(DBServiceImpl.class.getName());

    private final Address address;
    private static final Database database = new Database();
    private final MessageSystemContext context;
    static {
        database.init();
        database.createScheme();        
    }

    ApplicationContext springContext;

    public DBServiceImpl() {
        this.address = null;
        this.context = null;
    }
    public DBServiceImpl(MessageSystemContext context, Address address) {
        this.address = address;
        this.context = context;        
    }
    
    @Override
    public String getLocalStatus() {
        return null;
    }
    
    @Override
    public void save(UserDataSet dataSet) {
        UserDataSetDao userDao = new UserDataSetDao(database.getConnection());
        userDao.save(dataSet);
    }

    @Override
    public UserDataSet read(long id) {
        CacheElement cacheElement = getCache().get(id);
        if (cacheElement!=null) {
            UserDataSet userFromCache = (UserDataSet) cacheElement.getValue();
            if (log.isLoggable(INFO)) log.info("read from cache id = " + id);
            return userFromCache;
        }
        
        UserDataSetDao userDao = new UserDataSetDao(database.getConnection());
        UserDataSet user = userDao.read(id);
        if (user!=null) {
            if (log.isLoggable(INFO)) log.info("read from db id = " + id);
            getCache().put( new CacheElement(id, user) );
            return user;
        } else {
            return null;
        }
        
    }

    @Override
    public UserDataSet readByName(String name) {
        return null;
    }

    @Override
    public List<UserDataSet> readAll() {
        return Collections.emptyList();
    }

    @Override
    public void shutdown() {
        try {
            getCache().dispose();
            database.getConnection().close();
            database.shutdown();
            if (log.isLoggable(INFO)) log.info("DBservice shutdown");
        } catch (SQLException e) {
            if (log.isLoggable(SEVERE)) log.log(SEVERE, "DB shutdown failed", e);
        }
    }

    private CacheEngine<Long, UserDataSet> getCache() {
        return (CacheEngine<Long, UserDataSet>) springContext.getBean("cacheEngine");
    }
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.springContext = applicationContext;
    }

    @Override
    public Address getAddress() {
        return address;
    }
    public void init() {
        context.getMessageSystem().addAddressee(this);
    }
    
}
