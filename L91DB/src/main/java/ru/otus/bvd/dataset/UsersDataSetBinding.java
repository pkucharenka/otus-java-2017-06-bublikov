package ru.otus.bvd.dataset;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UsersDataSetBinding implements DataSetBinding<UsersDataSet> {


	@Override
	public UsersDataSet handle(ResultSet resultSet) throws SQLException {
		if (resultSet.next()) {
			long id = resultSet.getLong("id");
			String name = resultSet.getString("name");
			Integer age = resultSet.getInt("age");
			UsersDataSet user = new UsersDataSet(name, age);
			user.id = id;
			return user;
		}
		
		return null;
	}

	@Override
	public String create(UsersDataSet dataSet) {
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO Z_USER (name, age) VALUES (")
			.append("'").append(dataSet.getName()).append("'")
			.append(",")
			.append(dataSet.getAge())
			.append(");");
		
		return sb.toString();
	}

	@Override
	public String select(long id) {
		return "SELECT id, name, age from Z_USER u where u.id=" + id;
	}

	@Override
	public String update(UsersDataSet dataSet) {
		return null;
	}

}
