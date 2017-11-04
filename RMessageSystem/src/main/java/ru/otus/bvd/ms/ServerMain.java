package ru.otus.bvd.ms;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import ru.otus.bvd.ms.app.ProcessRunner;
import ru.otus.bvd.ms.core.MessageSystem;
import ru.otus.bvd.ms.core.MessageSystemContext;
import ru.otus.bvd.ms.runner.ProcessRunnerImpl;
import ru.otus.bvd.ms.server.BlockingEchoSocketMsgServer;

/**
 * Created by tully.
 */
public class ServerMain {
    private static final Logger logger = Logger.getLogger(ServerMain.class.getName());

    private static final String CLIENT_START_COMMAND = "java -jar ../RMessageClient/target/client.jar ${instanceId}";
    private static final String FRONTENDSERVICE_START_COMMAND_1 = "java -jar ../RFrontendService/target/frontendservice.jar 8080";
    private static final String FRONTENDSERVICE_START_COMMAND_2 = "java -jar ../RFrontendService/target/frontendservice.jar 8090";
    private static final String DBSERVICE_START_COMMAND = "java -jar ../RDBService/target/rdbservice.jar";
    private static final int CLIENT_START_DELAY_SEC = 1;
    private static final int CLIENTS_COUNT = 0;

    public static void main(String[] args) throws Exception {
    	configureLog(Level.ALL);
    	new ServerMain().start();
    }

    private void start() throws Exception {
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();        
        startClients(executorService);
        startBlockingServer();        
        executorService.shutdown();
    }

    private void startBlockingServer() throws Exception {
    	MessageSystem messageSystem = new MessageSystem();
        MessageSystemContext messageSystemContext = new MessageSystemContext(messageSystem);
        messageSystem.init();
        messageSystemContext.addAddresse(messageSystem);
    	messageSystem.start();
    	new BlockingEchoSocketMsgServer(messageSystemContext).start();
    }

    private void startClients(ScheduledExecutorService executorService) throws InterruptedException {
    	startClient(executorService, DBSERVICE_START_COMMAND, CLIENT_START_DELAY_SEC);
    	startClient(executorService, DBSERVICE_START_COMMAND, CLIENT_START_DELAY_SEC + 1);
    	startClient(executorService, FRONTENDSERVICE_START_COMMAND_1, CLIENT_START_DELAY_SEC + 2);
    	startClient(executorService, FRONTENDSERVICE_START_COMMAND_2, CLIENT_START_DELAY_SEC + 7);
    }
    
    
    private void startClient(ScheduledExecutorService executorService, String script, int delay) {
    	executorService.schedule(() -> {
            try {
                ProcessRunner processRunner = new ProcessRunnerImpl();
                processRunner.start(script);                
            } catch (IOException e) {
                logger.log(Level.SEVERE, e.getMessage());
            }
        }, delay, TimeUnit.SECONDS);    	
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
