package ru.otus.bvd.ms.messages;

import ru.otus.bvd.ms.app.Msg;
import ru.otus.bvd.ms.core.Address;
import ru.otus.bvd.ms.core.AddressGroup;
import ru.otus.bvd.ms.core.Addressee;

/**
 * Created by tully.
 */
public abstract class MsgToDB extends Msg {
    private long requestId;
    	
	public MsgToDB(Class<?> klass, Address from, Address to, long requestId) {
        super(klass, from, to);
        this.requestId = requestId;        
    }

}
