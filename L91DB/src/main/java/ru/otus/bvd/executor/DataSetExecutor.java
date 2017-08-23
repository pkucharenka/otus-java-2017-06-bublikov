package ru.otus.bvd.executor;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import ru.otus.bvd.dataset.DataSet;
import ru.otus.bvd.dataset.DataSetBinding;

public class DataSetExecutor {
	private final DataSetBinding binding;
    private final Connection connection;
	
	
	public DataSetExecutor(Connection connection, DataSetBinding<? extends DataSet> binding) {
		this.connection = connection;
		this.binding = binding;
	}
	
	public <T extends DataSet> void save(T dataSet) {
		String sqlStatment = binding.create(dataSet);
		try {
	        try (Statement stmt = connection.createStatement()) {
	            stmt.execute(sqlStatment);
	        }
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public <T extends DataSet> T load(long id, Class<T> clazz){
		String sqlStatment = binding.select(id);
		
		T value = null;
		try (Statement stmt = connection.createStatement()){
	        stmt.execute(sqlStatment);
	        ResultSet result = stmt.getResultSet();
	        value = (T) binding.handle(result);
	        result.close();
	        stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

        return value;
	}	

}
