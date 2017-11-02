package ru.otus.bvd.ms.server;

import ru.otus.bvd.ms.app.Msg;
import ru.otus.bvd.ms.app.MsgClient;
import ru.otus.bvd.ms.channel.SocketMsgClient;
import ru.otus.bvd.ms.core.Address;
import ru.otus.bvd.ms.core.Addressee;
import ru.otus.bvd.ms.core.MessageSystemContext;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by tully.
 */
public class BlockingEchoSocketMsgServer {
    private static final Logger logger = Logger.getLogger(BlockingEchoSocketMsgServer.class.getName());

    private static final int THREADS_NUMBER = 1;
    private static final int PORT = 5050;
    private static final int ECHO_DELAY = 100;

    private final ExecutorService executor;
    private final List<SocketMsgClient> clients;
    private final MessageSystemContext messageSystemContext;

    public BlockingEchoSocketMsgServer(MessageSystemContext messageSystemContext) {
        executor = Executors.newFixedThreadPool(THREADS_NUMBER);
        clients = new CopyOnWriteArrayList<>();
        this.messageSystemContext = messageSystemContext;
    }

    public void start() throws Exception {
        executor.submit(this::echo);

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            logger.finest("Server started on port: " + serverSocket.getLocalPort());
            while (!executor.isShutdown()) {
                Socket socket = serverSocket.accept(); //blocks
                SocketMsgClient client = new SocketMsgClient(socket, messageSystemContext);
                client.init();
                client.addShutdownRegistration(() -> {
                	clients.remove(client);
                	messageSystemContext.getMessageSystem().stop(client);
                	client.executor.shutdownNow();
                });
                clients.add(client);
            }
        }
    }

    @SuppressWarnings("InfiniteLoopStatement")
    private void echo() {
        while (true) {
            for (SocketMsgClient client : clients) {
                Msg msg = client.pool(); //get
                while (msg != null) {
                    if (client.getAddress() == null) {
                    	Address clientAddress = new Address(msg.getFrom().getAddressGroup(), msg.getFrom().getId());
                    	logger.config("New address = " + clientAddress.getAddressGroup() + " " + clientAddress.getId() + " " + clientAddress);
                    	client.setAddress( clientAddress );
                    	messageSystemContext.addAddresse(client);
                    	messageSystemContext.getMessageSystem().addAddressee(client);
                    }                    	
                	Address from = messageSystemContext.getAddresse( msg.getFrom().getAddressGroup(), msg.getFrom().getId() ).getAddress(); 
                    Addressee toAddressee = messageSystemContext.getAddresse( msg.getTo().getAddressGroup(), msg.getTo().getId() );
                    if (toAddressee != null) {
	                	Address to =  messageSystemContext.getAddresse( msg.getTo().getAddressGroup(), msg.getTo().getId() ).getAddress();
	                    msg.setFrom(from);
	                    msg.setTo(to);
	                    msg.setMessageSystem(messageSystemContext.getMessageSystem());
	                    messageSystemContext.getMessageSystem().sendMessage(msg);
                    }    
                    msg = client.pool();                    
                }
            }
            try {
                Thread.sleep(ECHO_DELAY);
            } catch (InterruptedException e) {
                logger.log(Level.SEVERE, e.toString());
            }
        }
    }
}
