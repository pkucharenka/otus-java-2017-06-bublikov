package ru.otus.bvd.ms.messages;

import java.io.Serializable;

import ru.otus.bvd.ms.core.Address;


/**
 * Created by tully.
 */
public class MsgGetUserById {//extends MsgToDB {
//    private final long userId;
//
//    public MsgGetUserById(Address from, Address to, long requestId, long userId) {
//        super(MsgGetUserById.class, from, to, requestId);
//        this.userId = userId;
//    }
//
//    @Override
//    public void exec(DBService dbService) {
//        UserDataSet user = dbService.read(userId);
//        if (user==null) {
//            messageSystem.sendMessage(new MsgGetUserByIdAnswer(getTo(), getFrom(), getRequestId(), "NO DATA FOUND", userId));
//            return;
//        }    
//        
//        String userName = user.toString();
//        messageSystem.sendMessage(new MsgGetUserByIdAnswer(getTo(), getFrom(), getRequestId(), userName, userId));
//    }
}
