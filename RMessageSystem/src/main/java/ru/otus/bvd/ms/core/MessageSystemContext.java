package ru.otus.bvd.ms.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
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
//    	for (Addressee addressee : recipients) {
//    		if (addressee.getAddress().getAddressGroup() == addressGroup)
//    			return addressee;
//    	}
    	return getAddresse(addressGroup, "ANY");
    }
    
    public Addressee getAddresse(AddressGroup addressGroup, String addressId) {
    	if ("ANY".equals(addressId)) {
    		List<Addressee> a = new ArrayList<>();
    		for (Addressee addressee : recipients) {
	    		Address addr = addressee.getAddress();	    		
	    		if (addr.getAddressGroup() == addressGroup)
	    			a.add(addressee);
	    	}    		
    		Random rand = new Random();
    		Addressee randomElement = a.get(rand.nextInt(a.size()));    
    		return randomElement;
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
