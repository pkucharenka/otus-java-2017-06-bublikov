package ru.otus.bvd.dao;

import java.sql.Connection;

import ru.otus.bvd.dataset.UserDataSet;
import ru.otus.bvd.executor.DataSetExecutor;

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
