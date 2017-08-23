package ru.otus.bvd.handlers;

import ru.otus.bvd.dataset.DataSet;

public interface SQLCook<T extends DataSet> {
	String create (T dataSet);
	
	String select (long id);
	
	String update (T dataSet);
}
