package ru.otus.bvd.dataset;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity(name="User")
@Table(name = "Z_USER")
public class UsersDataSet extends DataSet {
	private String name;
	private Integer age;

	public UsersDataSet(String name, Integer age){
		this.name = name;
		this.age = age;
	}

	@Column(name="NAME", nullable=true, updatable=true)
	public String getName() {
		return name;
	}

	@Column(name="AGE", nullable=false, updatable=true)
	public Integer getAge() {
		return age;
	}

	@Override
	public String toString() {
		return "id="+id+" , name="+name+", age= "+age;
	}
	
	
}	
