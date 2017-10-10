package ru.otus.bvd.messagesystem;

/**
 * @author tully
 */
public abstract class Message {
    private final Address from;
    private final Address to;
    private final long requestId;

    public Message(Address from, Address to, long requestId) {
        this.from = from;
        this.to = to;
        this.requestId = requestId;
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
