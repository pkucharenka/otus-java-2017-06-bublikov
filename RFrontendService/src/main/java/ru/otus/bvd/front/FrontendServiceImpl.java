package ru.otus.bvd.front;


import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

import ru.otus.bvd.ms.app.FrontSocket;
import ru.otus.bvd.ms.app.FrontendService;


/**
 * Created by tully.
 */
public class FrontendServiceImpl implements FrontendService {
    private static final ConcurrentHashMap<Long, FrontSocket> requests = new ConcurrentHashMap<>();
    private static final Logger logger = Logger.getLogger(FrontendServiceImpl.class.getName());

    public void handleRequest(long userId, long requestId, FrontSocket frontSocket) {
        requests.put(requestId, frontSocket);
//        Message message = new MsgGetUserById(context.getMessageSystem(), getAddress(), context.getDbAddress(), requestId, userId);
//        context.getMessageSystem().sendMessage(message);
//        clientService.getClient().send(message);
    }

    public void sendUser(long id, String name, long requestId) {
        //requests.get(requestId).sendResponse(name);
    }

    
    private static volatile FrontendServiceImpl instance;
    public static FrontendServiceImpl getInstance() {
    	FrontendServiceImpl localInstance = instance;
    	if (localInstance == null) {
    		synchronized (FrontendServiceImpl.class) {
			localInstance = instance;
			if (localInstance == null) {
				instance = localInstance = new FrontendServiceImpl();
				}
    		}
    	}
    	return localInstance;
    }    
}
