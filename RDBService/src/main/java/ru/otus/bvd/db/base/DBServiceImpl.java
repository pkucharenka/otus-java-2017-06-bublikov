package ru.otus.bvd.db.base;

import static java.util.logging.Level.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

import ru.otus.bvd.db.Database;
import ru.otus.bvd.db.cache.CacheElement;
import ru.otus.bvd.db.cache.CacheEngine;
import ru.otus.bvd.db.cache.CacheEngineImpl;
import ru.otus.bvd.db.dao.UserDataSetDao;
import ru.otus.bvd.ms.app.DBService;
import ru.otus.bvd.ms.dataset.UserDataSet;

public class DBServiceImpl implements DBService {
    private static final Logger log = Logger.getLogger(DBServiceImpl.class.getName());

    private static final Database database = new Database();

    public void dbStart() {
        database.init();
        database.createScheme();            	
    }
    
    @Override
    public String getLocalStatus() {
        return null;
    }
    
    @Override
    public void save(UserDataSet dataSet) {
        Connection connection = database.getConnection();
    	UserDataSetDao userDao = new UserDataSetDao(connection);
        userDao.save(dataSet);
        try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }

    @Override
    public UserDataSet read(long id) {
        CacheElement cacheElement = getCache().get(id);
        if (cacheElement!=null) {
            UserDataSet userFromCache = (UserDataSet) cacheElement.getValue();
            //if (log.isLoggable(CONFIG)) log.config("read from cache id = " + id);
            return userFromCache;
        }
        
        Connection connection = database.getConnection();
        UserDataSetDao userDao = new UserDataSetDao(connection);
        UserDataSet user = userDao.read(id);
        try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
        if (user!=null) {
            //if (log.isLoggable(CONFIG)) log.config("read from db id = " + id);
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
            if (log.isLoggable(CONFIG)) log.config("DBservice shutdown");
        } catch (SQLException e) {
            if (log.isLoggable(SEVERE)) log.log(SEVERE, "DB shutdown failed", e);
        }
    }

    private CacheEngine<Long, UserDataSet> cache;
    private CacheEngine<Long, UserDataSet> getCache() {
    	if (cache == null)
    		cache = new CacheEngineImpl<>(1000, 1000, 1000, false);
    	return cache;
    }

    private static volatile DBServiceImpl instance;
    public static DBServiceImpl getInstance() {
    	DBServiceImpl localInstance = instance;
    	if (localInstance == null) {
    		synchronized (DBServiceImpl.class) {
			localInstance = instance;
			if (localInstance == null) {
				instance = localInstance = new DBServiceImpl();
				}
    		}
    	}
    	return localInstance;
    }    
    
}
