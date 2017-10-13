package ru.otus.bvd.server;


import javax.management.MBeanServer;
import javax.management.ObjectName;

import ru.otus.bvd.runner.ProcessRunnerImpl;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by tully.
 */
public class ServerMain {
    private static final Logger logger = Logger.getLogger(ServerMain.class.getName());

    private static final String CLIENT_START_COMMAND = "java -jar ../L16MessageServerClient/target/L16MessageServerClient.jar";
    private static final int CLIENT_START_DELAY_SEC = 1;
    private static final int CLIENTS_COUNT = 1;

    public static void main(String[] args) throws Exception {
        new ServerMain().start();
    }

    public void start() throws Exception {
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        //startClients(CLIENTS_COUNT, executorService);

        startBlockingServer();

        executorService.shutdown();
    }


    private void startBlockingServer() throws Exception {
        new BlockingEchoSocketMsgServer().start();
    }

    private void startClients(int count, ScheduledExecutorService executorService) {
        for (int i = 0; i < count; i++) {
            executorService.schedule(() -> {
                try {
                    new ProcessRunnerImpl().start(CLIENT_START_COMMAND);
                } catch (IOException e) {
                    logger.log(Level.SEVERE, e.getMessage());
                }
            }, CLIENT_START_DELAY_SEC + i, TimeUnit.SECONDS);
        }
    }

}
