package ru.otus.bvd.ms.messages;

import java.util.logging.Logger;

import ru.otus.bvd.ms.app.Msg;
import ru.otus.bvd.ms.app.MsgClient;
import ru.otus.bvd.ms.core.Address;
import ru.otus.bvd.ms.core.Addressee;

/**
 * Created by tully.
 */
public class PingMsgRs extends Msg {
	private static final Logger logger = Logger.getLogger(PingMsgRs.class.getName());
	
	private final long time;
    private final String pid;

    public PingMsgRs(String pid, Address from, Address to) {
        super(PingMsgRs.class, from, to);
        this.pid = pid;
        time = System.currentTimeMillis();
    }

    public long getTime() {
        return time;
    }

    public String getPid() {
        return pid;
    }

    @Override
    public String toString() {
        return "PingMsgRs{" + "pid='" + pid + ", time=" + time + '\'' + '}';
    }

	@Override
	public void exec(Addressee addressee) {
        if (addressee instanceof MsgClient) {
    		logger.finest("Echoing the message: " + this.toString());
            MsgClient msgClient = (MsgClient) addressee;	
            msgClient.send(this);
        }
	}
}
