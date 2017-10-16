package ru.otus.bvd.client;

import ru.otus.bvd.app.SocketMsgClient;
import ru.otus.bvd.front.FrontendServiceImpl;
import ru.otus.bvd.messagesystem.Message;
import ru.otus.bvd.messagesystem.PingMsg;

import java.lang.management.ManagementFactory;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by vadim on 14.10.17.
 */
public class ClientServiceImpl implements ClientService {

    private static final String HOST = "localhost";
    private static final int PORT = 5050;
    private static final int PAUSE_MS = 5000;
    private static final int MAX_MESSAGES_COUNT = 5;
    private static final Logger logger = Logger.getLogger(ClientServiceImpl.class.getName());

    private SocketMsgClient client;

    public void start() throws Exception {
        String pid = ManagementFactory.getRuntimeMXBean().getName();

        client = new ManagedMsgSocketClient(HOST, PORT);
        client.init();

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(() -> {
            try {
                while (true) {
                    Object msg = client.take();
                    System.out.println("Message received: " + msg.toString());
                }
            } catch (InterruptedException e) {
                logger.log(Level.SEVERE, e.getMessage());
            }
        });

        int count = 0;
        while (count < MAX_MESSAGES_COUNT) {
            Message msg = new PingMsg(pid);
            client.send(msg);
            System.out.println("Message sent: " + msg.toString());
            Thread.sleep(PAUSE_MS);
            count++;
        }
        client.close();
        executorService.shutdown();
    }

    @Override
    public SocketMsgClient getClient() {
        return client;
    }
}
