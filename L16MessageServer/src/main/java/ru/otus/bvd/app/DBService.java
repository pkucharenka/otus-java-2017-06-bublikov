package ru.otus.bvd.app;

import ru.otus.bvd.dataset.UserDataSet;

import java.util.List;

/**
 * @author v.chibrikov
 */
public interface DBService {
    String getLocalStatus();

    void save(UserDataSet dataSet);

    UserDataSet read(long id);

    UserDataSet readByName(String name);

    List<UserDataSet> readAll();

    void shutdown();
}
