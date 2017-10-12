package ru.otus.bvd.app;

import ru.otus.bvd.base.DBService;
import ru.otus.bvd.messagesystem.Address;
import ru.otus.bvd.messagesystem.Addressee;
import ru.otus.bvd.messagesystem.Message;

/**
 * Created by tully.
 */
public abstract class MsgToDB extends Message {
    public MsgToDB(Address from, Address to, long requestId) {
        super(from, to, requestId);
    }

    @Override
    public void exec(Addressee addressee) {
        if (addressee instanceof DBService) {
            exec((DBService) addressee);
        }
    }

    public abstract void exec(DBService dbService);
}
