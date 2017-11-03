package ru.otus.bvd.mc;

import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import ru.otus.bvd.ms.core.Address;
import ru.otus.bvd.ms.core.AddressGroup;
import ru.otus.bvd.ms.messages.MsgGetUserByIdAnswer;


/**
 * Created by tully.
 */
public class ClientMain {
    private static final Logger logger = Logger.getLogger(ClientMain.class.getName());

    private static final int PAUSE_MS = 5000;
    private static final int MAX_MESSAGES_COUNT = 500;

    public static void main(String[] args) throws Exception {
    	configureLog(Level.ALL);
    	AddressGroup addressGroup = null;
    	if (args.length != 0) {
    		addressGroup = AddressGroup.valueOf(args[0]);
    	} else {
    		logger.severe("В аргументе должна быть указана принадлежность к группе адресов");
    		return;
    	}
    	
//    	ManagedMsgSocketClient socketClient = new ManagedMsgSocketClient(ManagedMsgSocketClient.HOST_DEFAULT, ManagedMsgSocketClient.PORT_DEFAULT, addressGroup);
//        socketClient.init();
//    	MessageSystemClient messageSystemClient = new MessageSystemClient(socketClient) {};
//    	messageSystemClient.init();
//    	
//    	MsgGetUserByIdAnswer msgUser = new MsgGetUserByIdAnswer(socketClient.getAddress(), new Address(AddressGroup.FRONTENDSERVICE, "ANY"), 1234, "BVD", 456);
//    	socketClient.send(msgUser);
//    	
//    	Thread.sleep(2000);
//    	messageSystemClient.stop();
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
