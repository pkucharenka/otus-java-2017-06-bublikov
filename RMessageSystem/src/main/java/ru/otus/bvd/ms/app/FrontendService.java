package ru.otus.bvd.ms.app;


/**
 * Created by tully.
 */
public interface FrontendService {
    void handleRequest(long id, long requestId, FrontSocket frontSocket);

    void sendUser(long id, String name, long requestId);    
}

