package ru.otus.bvd.db.main;

import java.io.IOException;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import ru.otus.bvd.db.base.DBServiceImpl;
import ru.otus.bvd.mc.ManagedMsgSocketClient;
import ru.otus.bvd.mc.MessageSystemClient;
import ru.otus.bvd.ms.app.DBService;
import ru.otus.bvd.ms.app.Msg;
import ru.otus.bvd.ms.core.AddressGroup;
import ru.otus.bvd.ms.messages.MsgToDB;
import ru.otus.bvd.ms.messages.PingMsgRs;

public class MainDBService {
	private static final Logger log = Logger.getLogger(MainDBService.class.getName());
	
	private ManagedMsgSocketClient socketClient;
    private MessageSystemClient messageSystemClient;
    private DBService dbService;

    public static void main(String[] args) {
		configureLog(Level.ALL);
		
		
		MainDBService mainInstance = new MainDBService();
		mainInstance.start();
	}

	private void start() {
		DBServiceImpl.getInstance().dbStart();
		dbService = DBServiceImpl.getInstance();
			
		startDBService();
		startDBActivity();
		startSocketClient();
		startMessageSystemClient();
	}
	
	private void startDBService() {
	}
	
	private void startDBActivity() {
        Thread dbActivity = new Thread( new DBActivity(dbService));
        dbActivity.start();                				
	}
	
	private void startSocketClient() {
		try {
			socketClient = new ManagedMsgSocketClient(ManagedMsgSocketClient.HOST_DEFAULT, ManagedMsgSocketClient.PORT_DEFAULT, AddressGroup.DBSERVICE);
		} catch (IOException e) {
			e.printStackTrace();
		}
		socketClient.init();
	}

	private void startMessageSystemClient() {
    	MessageSystemClient messageSystemClient = new MessageSystemClient(socketClient) {
    		@Override
    		public void process(Msg msg) {
    			super.process(msg);
    			if (msg instanceof PingMsgRs) {
        			return;
    			} else if (msg instanceof MsgToDB) {
    				MsgToDB msgToDB = (MsgToDB) msg;
    				msgToDB.setDBService(dbService);
    				msgToDB.setSocketMsgClient(socketClient);
    				msgToDB.execEndPoint();    				
    			}
    		}
    	};
    	messageSystemClient.init();
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
