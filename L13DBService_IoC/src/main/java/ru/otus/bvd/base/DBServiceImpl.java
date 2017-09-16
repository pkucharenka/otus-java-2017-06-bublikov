package ru.otus.bvd.base;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import ru.otus.bvd.cache.CacheElement;
import ru.otus.bvd.cache.CacheEngine;
import ru.otus.bvd.cache.CacheEngineAdmin;
import ru.otus.bvd.cache.CacheEngineImpl;
import ru.otus.bvd.dao.UserDataSetDao;
import ru.otus.bvd.dataset.UserDataSet;
import ru.otus.bvd.db.Database;

public class DBServiceImpl implements DBService, ApplicationContextAware {
    private static final Database database = new Database();
    static {
        database.init();
        database.createScheme();        
    }

    ApplicationContext springContext;

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
            System.out.println("read from cache id = " + id);
            return userFromCache;
        }
        
        UserDataSetDao userDao = new UserDataSetDao(database.getConnection());
        UserDataSet user = userDao.read(id);
        if (user!=null) {
            System.out.println("read from db id = " + id);
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
        return null;
    }

    @Override
    public void shutdown() {
        try {
            getCache().dispose();
            database.getConnection().close();
            database.shutdown();
            System.out.println("DBservice shutdown");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private CacheEngine<Long, UserDataSet> getCache() {
        return (CacheEngine<Long, UserDataSet>) springContext.getBean("cacheEngine");
    }
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.springContext = applicationContext;
    }
}
