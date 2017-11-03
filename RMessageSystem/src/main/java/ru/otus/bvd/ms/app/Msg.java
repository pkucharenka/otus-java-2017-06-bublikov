package ru.otus.bvd.ms.app;

import ru.otus.bvd.ms.channel.SocketMsgClient;
import ru.otus.bvd.ms.core.Address;
import ru.otus.bvd.ms.core.Addressee;
import ru.otus.bvd.ms.core.MessageSystem;

/**
 * Created by tully.
 */
public abstract class Msg {
    public static final String CLASS_NAME_VARIABLE = "className";
    
    protected Address from;
    protected Address to;
    protected transient MessageSystem messageSystem;    
    public SocketMsgClient socketMsgClient;
    
    @SuppressWarnings({"FieldCanBeLocal", "unused"})
    private final String className;
    
    protected Msg(Class<?> klass, Address from, Address to) {
        this.className = klass.getName();
        this.from = from;
        this.to = to;
    }
    
    public Address getFrom() {
        return from;
    }

    public Address getTo() {
        return to;
    }

    public void setFrom(Address address) {
    	this.from = address;
    }
    
    public void setTo(Address address) {
    	this.to = address;
    }
    
    public void setMessageSystem(MessageSystem messageSystem) {
    	this.messageSystem = messageSystem;
    }
    public MessageSystem getMessageSystem() {
    	return messageSystem;
    }
    public void setSocketMsgClient(SocketMsgClient socketMsgClient) {
    	this.socketMsgClient = socketMsgClient;
    }
    
    public void execMS(Addressee addressee) {
        if (addressee instanceof MsgClient) {
        	MsgClient msgClient = (MsgClient) addressee;
        	msgClient.send(this);
        }	
    }

    public abstract void execEndPoint();
}
