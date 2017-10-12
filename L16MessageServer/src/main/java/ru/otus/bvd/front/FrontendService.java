package ru.otus.bvd.front;

/**
 * Created by tully.
 */
public interface FrontendService {
    void init();

    void handleRequest(long id, long requestId, FrontSocket frontSocket);

    void addUser(long id, String name, long requestId);
}

