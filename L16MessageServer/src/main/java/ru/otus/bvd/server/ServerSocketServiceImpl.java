package ru.otus.bvd.server;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

public class ServerSocketServiceImpl implements ServerSocketService {
    private static final Logger logger = Logger.getLogger(ServerSocketServiceImpl.class.getName());
    
    private final BlockingEchoSocketMsgServer socketServer;
    
    public ServerSocketServiceImpl() {
        this.socketServer = new BlockingEchoSocketMsgServer();
    }
    
    public void start() {
        try {
            ExecutorService executorService = Executors.newSingleThreadExecutor();
            executorService.submit( ()-> {
                try {
                    socketServer.start();
                } catch (Exception e) {
                    Thread.currentThread().interrupt();
                }           
            } );
            
        } catch (Exception e) {
            logger.severe(e.getMessage());
        }
    }
   
    
    
    @Override
    public BlockingEchoSocketMsgServer getSocketServer() {
        return socketServer;
    }

}
