package ru.otus.bvd.ms.messages;

import java.util.logging.Logger;

import ru.otus.bvd.ms.app.Msg;
import ru.otus.bvd.ms.core.Address;
import ru.otus.bvd.ms.core.Addressee;

/**
 * Created by tully.
 */
public class PingMsgRq extends Msg {
	private static final Logger logger = Logger.getLogger(PingMsgRq.class.getName());
	
	private final long time;
    private final String pid;
    

    public PingMsgRq(String pid, Address from, Address to) {
        super(PingMsgRq.class, from, to);
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
        return "PingMsgRq{" + "pid='" + pid + ", time=" + time + '\'' + '}';
    }

	@Override
	public void exec(Addressee addressee) {
		getMessageSystem().sendMessage( new PingMsgRs(pid, to, from) );
	}
}
