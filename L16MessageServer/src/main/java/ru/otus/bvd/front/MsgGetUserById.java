package ru.otus.bvd.front;

import ru.otus.bvd.app.MsgToDB;
import ru.otus.bvd.base.DBService;
import ru.otus.bvd.dataset.UserDataSet;
import ru.otus.bvd.messagesystem.Address;
import ru.otus.bvd.messagesystem.MessageSystem;

/**
 * Created by tully.
 */
public class MsgGetUserById extends MsgToDB {
    private final MessageSystem messageSystem;
    private final long userId;

    public MsgGetUserById(MessageSystem messageSystem, Address from, Address to, long requestId, long userId) {
        super(MsgGetUserById.class, from, to, requestId);
        this.userId = userId;
        this.messageSystem = messageSystem;
    }

    @Override
    public void exec(DBService dbService) {
        UserDataSet user = dbService.read(userId);
        if (user==null) {
            messageSystem.sendMessage(new MsgGetUserByIdAnswer(getTo(), getFrom(), getRequestId(), "NO DATA FOUND", userId));
            return;
        }    
        
        String userName = user.toString();
        messageSystem.sendMessage(new MsgGetUserByIdAnswer(getTo(), getFrom(), getRequestId(), userName, userId));
    }
}
