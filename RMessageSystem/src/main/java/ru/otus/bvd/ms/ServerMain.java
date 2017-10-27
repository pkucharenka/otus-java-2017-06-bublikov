package ru.otus.bvd.ms;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.logging.StreamHandler;

import ru.otus.bvd.ms.app.ProcessRunner;
import ru.otus.bvd.ms.runner.ProcessRunnerImpl;
import ru.otus.bvd.ms.server.BlockingEchoSocketMsgServer;

/**
 * Created by tully.
 */
public class ServerMain {
    private static final Logger logger = Logger.getLogger(ServerMain.class.getName());

    private static final String CLIENT_START_COMMAND = "java -jar ../RMessageClient/target/client.jar ${instanceId}";
    private static final int CLIENT_START_DELAY_SEC = 1;
    private static final int CLIENTS_COUNT = 0;

    public static void main(String[] args) throws Exception {
    	configureLog(Level.ALL);
    	new ServerMain().start();
    }

    private void start() throws Exception {
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        
        startClients(CLIENTS_COUNT, executorService);

        startBlockingServer();

        executorService.shutdown();
    }

    private void startBlockingServer() throws Exception {
        new BlockingEchoSocketMsgServer().start();
    }

    private AtomicInteger clientInstanceCounter = new AtomicInteger(0);
    private void startClients(int count, ScheduledExecutorService executorService) {
        for (int i = 0; i < count; i++) {
            executorService.schedule(() -> {
                try {
                    ProcessRunner processRunner = new ProcessRunnerImpl();
                    processRunner.start(CLIENT_START_COMMAND.replace("${instanceId}", ""+clientInstanceCounter.getAndIncrement()));
                    
                } catch (IOException e) {
                    logger.log(Level.SEVERE, e.getMessage());
                }
            }, CLIENT_START_DELAY_SEC + i, TimeUnit.SECONDS);
        }
    }

    private static void configureLog(Level level) {
        System.setProperty("java.util.logging.SimpleFormatter.format", "SERVER: %1$tF %1$tT %4$s %2$s %5$s%6$s%n");
        Logger.getGlobal().setLevel(level);
        Logger rootLogger = LogManager.getLogManager().getLogger("");
        rootLogger.setLevel(level);
        for (Handler h : rootLogger.getHandlers()) {
            h.setLevel(level);            
        }                  
    }
    
    
}
