package ru.otus.bvd.base;

import java.sql.SQLException;
import java.util.List;

import ru.otus.bvd.cache.CacheElement;
import ru.otus.bvd.cache.CacheEngine;
import ru.otus.bvd.cache.CacheEngineAdmin;
import ru.otus.bvd.cache.CacheEngineImpl;
import ru.otus.bvd.dao.UserDataSetDao;
import ru.otus.bvd.dataset.UserDataSet;
import ru.otus.bvd.db.Database;

public class DBServiceImpl implements DBService, DBServiceAdmin {
    private static final Database database = new Database();
    static {
        database.init();
        database.createScheme();        
    }
    
    CacheEngine<Long, UserDataSet> cacheUser = new CacheEngineImpl<>(1000, 10000, 1000, false);    
    
    public CacheEngineAdmin getCacheEngine() {
        return (CacheEngineAdmin) cacheUser;
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
        CacheElement cacheElement = cacheUser.get(id);
        if (cacheElement!=null) {
            UserDataSet userFromCache = (UserDataSet) cacheElement.getValue();
            System.out.println("read from cache id = " + id);
            return userFromCache;
        }
        
        UserDataSetDao userDao = new UserDataSetDao(database.getConnection());
        UserDataSet user = userDao.read(id);
        if (user!=null) {
            System.out.println("read from db id = " + id);
            cacheUser.put( new CacheElement(id, user) );
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
            cacheUser.dispose();
            database.getConnection().close();
            database.shutdown();
            System.out.println("DBservice shutdown");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
