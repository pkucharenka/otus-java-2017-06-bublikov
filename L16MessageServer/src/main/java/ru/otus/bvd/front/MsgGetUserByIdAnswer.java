package ru.otus.bvd.front;

import ru.otus.bvd.messagesystem.Address;

/**
 * Created by tully.
 */
public class MsgGetUserByIdAnswer extends MsgToFrontend {
    private final String name;
    private final long id;

    public MsgGetUserByIdAnswer(Address from, Address to, long requestId, String name, long id) {
        super(MsgGetUserByIdAnswer.class, from, to, requestId);
        this.name = name;
        this.id = id;
    }

    @Override
    public void exec(FrontendService frontendService) {
        frontendService.addUser(id, name, getRequestId());
    }
}
