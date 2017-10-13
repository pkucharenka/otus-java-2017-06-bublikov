package ru.otus.bvd.messagesystem;

/**
 * @author tully
 */
public abstract class Message {
    public static final String CLASS_NAME_VARIABLE = "className";
    private final String className;

    private final Address from;
    private final Address to;
    private final long requestId;

    public Message(Class<?> klass, Address from, Address to, long requestId) {
        this.from = from;
        this.to = to;
        this.requestId = requestId;
        this.className = klass.getName();
    }
    public Message(Class<?> klass) {
        this.from = null;
        this.to = null;
        this.requestId = 0;
        this.className = klass.getName();
    }

    public Address getFrom() {
        return from;
    }

    public Address getTo() {
        return to;
    }
    public long getRequestId() {
        return requestId;
    }

    public abstract void exec(Addressee addressee);
}
