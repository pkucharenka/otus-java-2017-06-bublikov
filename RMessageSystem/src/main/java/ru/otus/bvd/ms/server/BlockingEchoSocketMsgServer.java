package ru.otus.bvd.ms.server;

import ru.otus.bvd.ms.app.Msg;
import ru.otus.bvd.ms.app.MsgClient;
import ru.otus.bvd.ms.channel.SocketMsgClient;

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
    private final List<MsgClient> clients;

    public BlockingEchoSocketMsgServer() {
        executor = Executors.newFixedThreadPool(THREADS_NUMBER);
        clients = new CopyOnWriteArrayList<>();
    }

    public void start() throws Exception {
        executor.submit(this::echo);

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            logger.finest("Server started on port: " + serverSocket.getLocalPort());
            while (!executor.isShutdown()) {
                Socket socket = serverSocket.accept(); //blocks
                SocketMsgClient client = new SocketMsgClient(socket);
                client.init();
                client.addShutdownRegistration(() -> clients.remove(client));
                clients.add(client);
            }
        }
    }

    @SuppressWarnings("InfiniteLoopStatement")
    private void echo() {
        while (true) {
            for (MsgClient client : clients) {
                Msg msg = client.pool(); //get
                while (msg != null) {
                    msg.exec(client);
                    client.send(msg);
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
