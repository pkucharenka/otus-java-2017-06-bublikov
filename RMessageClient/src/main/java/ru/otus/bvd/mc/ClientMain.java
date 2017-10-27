package ru.otus.bvd.mc;

import java.lang.management.ManagementFactory;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import ru.otus.bvd.ms.app.Msg;
import ru.otus.bvd.ms.channel.SocketMsgClient;
import ru.otus.bvd.ms.core.Address;
import ru.otus.bvd.ms.core.AddressGroup;
import ru.otus.bvd.ms.messages.PingMsg;


/**
 * Created by tully.
 */
public class ClientMain {
    private static final Logger logger = Logger.getLogger(ClientMain.class.getName());

    private static final String HOST = "localhost";
    private static final int PORT = 5050;
    private static final int PAUSE_MS = 5000;
    private static final int MAX_MESSAGES_COUNT = 5;

    public static void main(String[] args) throws Exception {
    	configureLog(Level.ALL);
    	new ClientMain().start();
    }

    private Address messageSystemAddress;
    
    @SuppressWarnings("InfiniteLoopStatement")
    private void start() throws Exception {
        String pid = ManagementFactory.getRuntimeMXBean().getName();

        SocketMsgClient client = new ManagedMsgSocketClient(HOST, PORT);
        messageSystemAddress = new Address(AddressGroup.MESSAGESYSTEM);
        client.init();

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(() -> {
            try {
                while (true) {
                    Object msg = client.take();
                    logger.fine(("Message received: " + msg.toString()));
                }
            } catch (InterruptedException e) {
                logger.log(Level.SEVERE, e.getMessage());
            }
        });

        int count = 0;
        while (count < MAX_MESSAGES_COUNT) {
            Msg msg = new PingMsg(pid, client.getAddress(), messageSystemAddress);
            client.send(msg);
            logger.fine(("Message sent: " + msg.toString()));
            Thread.sleep(PAUSE_MS);
            count++;
        }
        client.close();
        executorService.shutdown();
        System.exit(0);
    }

    private static void configureLog(Level level) {
        System.setProperty("java.util.logging.SimpleFormatter.format", "CLIENT: %1$tF %1$tT %4$s %2$s %5$s%6$s%n");
        Logger.getGlobal().setLevel(level);
        Logger rootLogger = LogManager.getLogManager().getLogger("");
        rootLogger.setLevel(level);
        for (Handler h : rootLogger.getHandlers()) {
            h.setLevel(level);
        }        
    }
    
    
}
