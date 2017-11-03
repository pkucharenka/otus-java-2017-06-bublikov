package ru.otus.bvd.fs.front;


import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

import ru.otus.bvd.ms.app.FrontSocket;
import ru.otus.bvd.ms.app.FrontendService;
import ru.otus.bvd.ms.app.Msg;
import ru.otus.bvd.ms.channel.SocketMsgClient;
import ru.otus.bvd.ms.core.Address;
import ru.otus.bvd.ms.core.AddressGroup;
import ru.otus.bvd.ms.messages.MsgGetUserById;


/**
 * Created by tully.
 */
public class FrontendServiceImpl implements FrontendService {
    private static final ConcurrentHashMap<Long, FrontSocket> requests = new ConcurrentHashMap<>();
    private static final Logger logger = Logger.getLogger(FrontendServiceImpl.class.getName());
    public SocketMsgClient socketClient;
    private Address dbAddress;
    
    public void handleRequest(long userId, long requestId, FrontSocket frontSocket) {
        if (dbAddress == null)
        	dbAddress = new Address(AddressGroup.DBSERVICE, "ANY");
    	
    	requests.put(requestId, frontSocket);
        Msg message = new MsgGetUserById(socketClient.getAddress(), dbAddress, requestId, userId);
        socketClient.send(message);
    }

    public void sendUser(long id, String name, long requestId) {
        requests.get(requestId).sendResponse(name);
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
