package ru.otus.bvd.mc;

import java.lang.management.ManagementFactory;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

import ru.otus.bvd.ms.app.Msg;
import ru.otus.bvd.ms.channel.SocketMsgClient;
import ru.otus.bvd.ms.core.Address;
import ru.otus.bvd.ms.core.AddressGroup;
import ru.otus.bvd.ms.messages.PingMsgRq;

public abstract class MessageSystemClient {
    private static final Logger logger = Logger.getLogger(MessageSystemClient.class.getName());
    private final ExecutorService executorTake;
    private final SocketMsgClient socketClient;
    private final Address messageSystemAddress;
   
    public MessageSystemClient(SocketMsgClient socketClient) {
    	this.executorTake = Executors.newSingleThreadExecutor();
    	this.socketClient = socketClient;
    	this.messageSystemAddress = new Address(AddressGroup.MESSAGESYSTEM, "ANY");
    }
    
    public void init() {
    	executorTake.submit(() -> {
            try {
                while (true) {
                    Msg msg = (Msg) socketClient.take();
                    process(msg);
                }
            } catch (InterruptedException e) {
                logger.log(Level.SEVERE, e.getMessage());
            }
        });

        String pid = ManagementFactory.getRuntimeMXBean().getName();
    	registrationOnMessageSysytem(pid);
    }
    
    private void registrationOnMessageSysytem(String pid) {
		Msg msg = new PingMsgRq(pid, socketClient.getAddress(), messageSystemAddress );
		socketClient.send(msg);
		logger.fine(("Registartion message sent: " + msg.toString()));    	
    }
    
    public void stop() {
        socketClient.close();
        executorTake.shutdown();    	
    }
    
    public void process(Msg msg) {
    	logger.fine(("Message received: " + msg.toString()));
    }
    
}
