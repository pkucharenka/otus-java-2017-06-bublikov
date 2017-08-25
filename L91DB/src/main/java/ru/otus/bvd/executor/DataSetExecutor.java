package ru.otus.bvd.executor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.*;

import org.h2.util.StringUtils;

import ru.otus.bvd.dataset.DataSet;
import ru.otus.bvd.dataset.UsersDataSet;

public class DataSetExecutor {
    private final Connection connection;
	
	
	public DataSetExecutor(Connection connection) {
		this.connection = connection;
	}
	
	public <T extends DataSet> void save(T dataSet) {
		String sqlStatment = toSqlInsert(dataSet);
		System.out.println(sqlStatment);
		
		try {
	        try (Statement stmt = connection.createStatement()) {
	            stmt.execute(sqlStatment);
	        }
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public <T extends DataSet> T load(long id, Class<T> clazz){
		String sqlStatment = toSqlSelect(id, clazz);
		System.out.println(sqlStatment);
		
		T value = null;
		try (Statement stmt = connection.createStatement()){
	        stmt.execute(sqlStatment);
	        ResultSet result = stmt.getResultSet();
	        value = toDataSet(result, clazz, id);
	        
	        result.close();
	        stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

        return value;
	}	
	
	private <T extends DataSet> String toSqlSelect(long id, Class<T> clazz) {
		StringBuilder query = new StringBuilder();
		String tableName = extractTableName(clazz);
		Map<String, String> columns = extractColumnValues(clazz, null);
		
		query.append("SELECT ")
			.append( String.join(", ", columns.keySet()) )
			.append(" FROM ")
			.append(tableName)
			.append(" WHERE ID = ")
			.append(id)
			.append(";");
		
		return query.toString();
	}
	
	
	private final static String INSERT_STATMENT_START = "INSERT INTO ";
	
	private <T extends DataSet> String toSqlInsert(T dataSet) {
		Class<?> dataSetClass = dataSet.getClass();
		String tableName = extractTableName(dataSetClass);
		Map<String, String> columnValues = extractColumnValues(dataSetClass, dataSet);
				
		StringBuilder columns = new StringBuilder();
		StringBuilder values = new StringBuilder();
		for (Map.Entry<String, String> entry : columnValues.entrySet()) {
			columns.append(entry.getKey()).append(", ");
			values.append(entry.getValue()).append(", ");
		}
		columns.setLength( columns.length()-2 );
		values.setLength( values.length()-2 );
		
		StringBuilder query = new StringBuilder();
		query
			.append(INSERT_STATMENT_START)
			.append(tableName)
			.append(" (").append(columns).append(") VALUES (").append(values).append(");"); 
		
		return query.toString();
	}
	
	private String wrapString(String s) {
		return "'"+s+"'";
	}
	
	private <T extends DataSet> T toDataSet (ResultSet result, Class<T> dataSetClass, long id) {
		T dataSet = null;
		try {
			dataSet = dataSetClass.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		
		try {
			if (!result.next()) return null;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try {
			Field fieldId = dataSetClass.getSuperclass().getDeclaredField("id");
			fieldId.setAccessible(true);
			fieldId.set(dataSet, new Long(id));
		} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
			e.printStackTrace();
		}
		//result to fields
		for(Field field : dataSetClass.getDeclaredFields() ) {
			field.setAccessible(true);
			for (Annotation annotation : field.getDeclaredAnnotations()) {
				if (annotation.annotationType()==Column.class) {
					for (Method annotationParam : annotation.annotationType().getDeclaredMethods()) {
		            	if ( "name".equals(annotationParam.getName()) ) {
							try {
								String columnName = (String) annotationParam.invoke(annotation, (Object[])null);
								field.set(dataSet, result.getObject(columnName) );								
							} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | SQLException e) {
								e.printStackTrace();
							}	            		
		            	}
					}
				}
			}
		}
		
		
		return dataSet;
	}
	
	private String extractTableName (Class<?> dataSetClass) {
		String tableName = null;

		for (Annotation annotation : dataSetClass.getDeclaredAnnotations()) {
			if (annotation.annotationType()==Table.class) {
	            for (Method method : annotation.annotationType().getDeclaredMethods()) {
	            	if ( "name".equals(method.getName()) ) {
						try {
							tableName = (String) method.invoke(annotation, (Object[])null);
						} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
							e.printStackTrace();
						}
						break;
	            	}
	            }				
			}
		}
				
		return tableName;
	}
	
	/**
	 * 
	 * @param dataSetClass
	 * @param dataSet - is optional
	 * @return
	 */
	private <T extends DataSet> Map<String, String> extractColumnValues(Class<?> dataSetClass, T dataSet) {
		Map<String, String> columnValues = new HashMap<>();

		for(Field field : dataSetClass.getDeclaredFields() ) {
			field.setAccessible(true);
			for (Annotation annotation : field.getDeclaredAnnotations()) {
				if (annotation.annotationType()==Column.class) {
					for (Method annotationParam : annotation.annotationType().getDeclaredMethods()) {
		            	if ( "name".equals(annotationParam.getName()) ) {
							try {
								String value = null;
								if (dataSet!=null) {
									value = field.get(dataSet).toString();
									value = (field.getType()==String.class) ? wrapString(value) : value;									
								}
								columnValues.put( (String) annotationParam.invoke(annotation, (Object[])null) ,  value);
							} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
								e.printStackTrace();
							}	            		
		            	}
					}
				}
			}
		}

		return columnValues;
	}
	
	
}
