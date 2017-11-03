package ru.otus.bvd.db.dao;

import java.sql.Connection;

import ru.otus.bvd.db.executor.DataSetExecutor;
import ru.otus.bvd.ms.dataset.UserDataSet;

public class UserDataSetDao {
    private final Connection connection;
    
    public UserDataSetDao(Connection connection) {
        this.connection = connection;
    }
    
    public void save(UserDataSet dataSet) {
        DataSetExecutor dsExecutor = new DataSetExecutor(connection);
        dsExecutor.save(dataSet);    
    }
 
    public UserDataSet read(long id) {
        DataSetExecutor dsExecutor = new DataSetExecutor(connection);
        return dsExecutor.load(id, UserDataSet.class);
    }
    
}
