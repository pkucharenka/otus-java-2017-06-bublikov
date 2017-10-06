package ru.otus.bvd.front;

import ru.otus.bvd.messagesystem.Address;
import ru.otus.bvd.messagesystem.Addressee;
import ru.otus.bvd.messagesystem.Message;

/**
 * Created by tully.
 */
public abstract class MsgToFrontend extends Message {
    public MsgToFrontend(Address from, Address to, long requestId) {
        super(from, to, requestId);
    }

    @Override
    public void exec(Addressee addressee) {
        if (addressee instanceof FrontendService) {
            exec((FrontendService) addressee);
        } else {
            //todo error!
        }
    }

    public abstract void exec(FrontendService frontendService);
}