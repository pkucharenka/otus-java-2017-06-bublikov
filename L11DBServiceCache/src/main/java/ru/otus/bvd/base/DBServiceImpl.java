package ru.otus.bvd.base;

import java.sql.SQLException;
import java.util.List;

import ru.otus.bvd.dao.UserDataSetDao;
import ru.otus.bvd.dataset.UserDataSet;
import ru.otus.bvd.db.Database;

public class DBServiceImpl implements DBService {
    private static final Database database = new Database();
    static {
        database.init();
        database.createScheme();        
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
        UserDataSetDao userDao = new UserDataSetDao(database.getConnection());
        return userDao.read(id);
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
            database.getConnection().close();
            database.shutdown();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
