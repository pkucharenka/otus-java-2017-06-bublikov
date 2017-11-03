package ru.otus.bvd.fs.main;

import java.io.IOException;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import ru.otus.bvd.fs.front.FrontendServiceImpl;
import ru.otus.bvd.fs.webserver.WebServer;
import ru.otus.bvd.mc.ManagedMsgSocketClient;
import ru.otus.bvd.mc.MessageSystemClient;
import ru.otus.bvd.ms.app.FrontendService;
import ru.otus.bvd.ms.app.Msg;
import ru.otus.bvd.ms.core.AddressGroup;
import ru.otus.bvd.ms.messages.MsgToFrontend;
import ru.otus.bvd.ms.messages.PingMsgRs;

public class MainFrontendService {
	private static final Logger log = Logger.getLogger(MainFrontendService.class.getName());

    private WebServer webServer;
    public ManagedMsgSocketClient socketClient;
    public MessageSystemClient messageSystemClient;

	public static void main(String[] args) {
		configureLog(Level.ALL);
		
		int port;
		if (args != null && args.length>0) {
			port = Integer.parseInt(args[0]);
		} else {
			port = 8080;
		}
		
		MainFrontendService mainInstance = new MainFrontendService();
		mainInstance.start(port);
		
	}
	
	private void start(int port) {
		startWebServer(port);
		startSocketClient();
		startMessageSystemClient();
		
	}

	private void startWebServer(int port) {
        webServer = new WebServer(port);
        webServer.init();
        webServer.start();
    }
	private void startSocketClient() {
		try {
			socketClient = new ManagedMsgSocketClient(ManagedMsgSocketClient.HOST_DEFAULT, ManagedMsgSocketClient.PORT_DEFAULT, AddressGroup.FRONTENDSERVICE);
		} catch (IOException e) {
			e.printStackTrace();
		}
		socketClient.init();
	}
	private void startMessageSystemClient() {
    	FrontendServiceImpl.getInstance().socketClient = socketClient;
		MessageSystemClient messageSystemClient = new MessageSystemClient(socketClient) {
    		FrontendService frontendService;
    		@Override
    		public void process(Msg msg) {
    			super.process(msg);
    			if (msg instanceof PingMsgRs) {
        			return;
    			} else if (msg instanceof MsgToFrontend) {
    				if (frontendService == null)
    					frontendService = FrontendServiceImpl.getInstance();
    				MsgToFrontend msgToFrontend = (MsgToFrontend) msg;    				
    				msgToFrontend.setFrontendService(frontendService);
    				msgToFrontend.execEndPoint();    				
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
