package ru.otus.bvd.ms.messages;

import ru.otus.bvd.ms.core.Address;
import ru.otus.bvd.ms.dataset.UserDataSet;


/**
 * Created by tully.
 */
public class MsgGetUserById extends MsgToDB { 
    private final long userId;

    public MsgGetUserById(Address from, Address to, long requestId, long userId) {
        super(MsgGetUserById.class, from, to, requestId);
        this.userId = userId;
    }


	@Override
	public void execEndPoint() {
        UserDataSet user = dbService.read(userId);
        if (user==null) {
            socketMsgClient.send(new MsgGetUserByIdAnswer(getTo(), getFrom(), requestId, "NO DATA FOUND", userId));
            return;
        }    
        
        String userName = user.toString();
        socketMsgClient.send(new MsgGetUserByIdAnswer(getTo(), getFrom(), requestId, userName, userId));
	}
}
