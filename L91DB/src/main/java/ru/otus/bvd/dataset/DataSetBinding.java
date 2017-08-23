package ru.otus.bvd.dataset;

import ru.otus.bvd.handlers.SQLCook;
import ru.otus.bvd.handlers.TResultHandler;

public interface DataSetBinding <T extends DataSet> extends SQLCook<T>, TResultHandler<T> {}
