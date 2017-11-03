package ru.otus.bvd.ms.dataset;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;


@Entity(name="User")
@Table(name = "Z_USER")
public class UserDataSet extends DataSet {
	@Column(name="NAME", nullable=true, updatable=true)
	private String name;

	@Column(name="AGE", nullable=false, updatable=true)
	private Integer age;

	@OneToOne(cascade = CascadeType.ALL, optional=true)
	private AddressDataSet address;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<PhoneDataSet> phones = new ArrayList<>();
		
	public UserDataSet(String name, Integer age, AddressDataSet address){
		this.setId(-1);
	    this.name = name;
		this.age = age;
		this.address = address;
	}
	public UserDataSet() {	}

	
    public List<PhoneDataSet> getPhones() {
        return phones;
    }
	
	@Override
	public String toString() {
		return "id="+getId()+" , name="+name+", age= "+age+", address="+address+", phones="+phones;
	}
	
	
}	
