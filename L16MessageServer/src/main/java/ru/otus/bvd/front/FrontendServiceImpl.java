package ru.otus.bvd.front;


import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

import ru.otus.bvd.app.MessageSystemContext;
import ru.otus.bvd.client.ClientSocketService;
import ru.otus.bvd.messagesystem.Address;
import ru.otus.bvd.messagesystem.Addressee;
import ru.otus.bvd.messagesystem.Message;

/**
 * Created by tully.
 */
public class FrontendServiceImpl implements FrontendService, Addressee {
    private final Address address;
    private final MessageSystemContext context;
    private final ClientSocketService clientService;
    private static final ConcurrentHashMap<Long, FrontSocket> requests = new ConcurrentHashMap<>();
    private static final Logger logger = Logger.getLogger(FrontendServiceImpl.class.getName());

    public FrontendServiceImpl(MessageSystemContext context, Address address, ClientSocketService clientService) {
        this.context = context;
        this.address = address;
        this.clientService = clientService;
    }


    public void init() {
        context.getMessageSystem().addAddressee(this);
    }

    @Override
    public Address getAddress() {
        return address;
    }

    public void handleRequest(long userId, long requestId, FrontSocket frontSocket) {
        requests.put(requestId, frontSocket);
        Message message = new MsgGetUserById(context.getMessageSystem(), getAddress(), context.getDbAddress(), requestId, userId);
        context.getMessageSystem().sendMessage(message);
        clientService.getClient().send(message);
    }

    public void addUser(long id, String name, long requestId) {
        requests.get(requestId).sendResponse(name);
    }
}
