package ru.otus.bvd.dataset;

import javax.persistence.Column;
import javax.persistence.Id;

public abstract class DataSet {
	protected long id;
	
	@Id
	@Column(name="ID", nullable=false, updatable=false)
	public long getId() {
		return id;
	}	
}
