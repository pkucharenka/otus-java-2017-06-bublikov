package ru.otus.bvd.messagesystem;


/**
 * Created by tully.
 */
public class PingMsg extends Message {
    private final long time;
    private final String pid;

    public PingMsg(String pid) {
        super(PingMsg.class);
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
        return "PingMsg{" + "pid='" + pid + ", time=" + time + '\'' + '}';
    }

    @Override
    public void exec(Addressee addressee) {
        // TODO Auto-generated method stub
        
    }
}
