package ru.otus.bvd.front;


import java.lang.management.ManagementFactory;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

import ru.otus.bvd.app.MessageSystemContext;
import ru.otus.bvd.app.SocketMsgClient;
import ru.otus.bvd.client.ClientService;
import ru.otus.bvd.client.ManagedMsgSocketClient;
import ru.otus.bvd.example.MainMessageSystem;
import ru.otus.bvd.messagesystem.Address;
import ru.otus.bvd.messagesystem.Addressee;
import ru.otus.bvd.messagesystem.Message;
import ru.otus.bvd.messagesystem.PingMsg;

/**
 * Created by tully.
 */
public class FrontendServiceImpl implements FrontendService, Addressee {
    private final Address address;
    private final MessageSystemContext context;
    private final ClientService clientService;
    private static final ConcurrentHashMap<Long, FrontSocket> requests = new ConcurrentHashMap<>();
    private static final Logger logger = Logger.getLogger(FrontendServiceImpl.class.getName());

    public FrontendServiceImpl(MessageSystemContext context, Address address, ClientService clientService) {
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
