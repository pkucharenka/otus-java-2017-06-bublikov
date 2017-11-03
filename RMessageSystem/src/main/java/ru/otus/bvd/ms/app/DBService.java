package ru.otus.bvd.ms.app;


import java.util.List;

import ru.otus.bvd.ms.dataset.UserDataSet;

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
