package ru.otus.bvd.ms.core;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by tully.
 */
public class MessageSystemContext {
    private final MessageSystem messageSystem;
    
    private List<Addressee> recipients = new CopyOnWriteArrayList<>();

    public MessageSystemContext(MessageSystem messageSystem) {
        this.messageSystem = messageSystem;
    }

    public MessageSystem getMessageSystem() {
        return messageSystem;
    }
    
    public Addressee getAddressee(AddressGroup addressGroup) {
    	for (Addressee addressee : recipients) {
    		if (addressee.getAddress().getAddressGroup() == addressGroup)
    			return addressee;
    	}
    	return null;
    }
    
    public Addressee getAddresse(AddressGroup addressGroup, String addressId) {
    	if ("ANY".equals(addressId)) {
	    	for (Addressee addressee : recipients) {
	    		Address addr = addressee.getAddress();
	    		if (addr.getAddressGroup() == addressGroup)
	    			return addressee;
	    	}    		
    	} else {
	    	for (Addressee addressee : recipients) {
	    		Address addr = addressee.getAddress();
	    		if (addr.getAddressGroup() == addressGroup && addr.getId().equals(addressId))
	    			return addressee;
	    	}
    	}	
    	return null;    	
    }
    
    public void addAddresse(Addressee addressee) {
    	recipients.add(addressee);
    }

}
