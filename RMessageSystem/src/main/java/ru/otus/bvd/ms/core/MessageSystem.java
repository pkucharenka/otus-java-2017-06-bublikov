package ru.otus.bvd.ms.core;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;

import ru.otus.bvd.ms.app.Msg;
import ru.otus.bvd.ms.channel.SocketMsgClient;

/**
 * @author tully
 */
public final class MessageSystem implements Addressee {
    private static final int DEFAULT_STEP_TIME = 10;

    private final Map<Address, ConcurrentLinkedQueue<Msg>> messagesMap = new HashMap<>();
    private final Map<Address, Addressee> addresseeMap = new HashMap<>();
    private final Map<Address, Thread> addrThreads = new ConcurrentHashMap<>();
    private Address selfAddress;
    
    public void addAddressee(Addressee addressee) {
        addresseeMap.put(addressee.getAddress(), addressee);
        messagesMap.put(addressee.getAddress(), new ConcurrentLinkedQueue<>());
        start();
    }

    public void sendMessage(Msg message) {
        messagesMap.get(message.getTo()).add(message);
    }
    
    public void init() {
    	selfAddress = new Address(AddressGroup.MESSAGESYSTEM, UUID.randomUUID().toString());
    	addAddressee(this);
    }
    
    @SuppressWarnings("InfiniteLoopStatement")
    public void start() {
        for (Map.Entry<Address, Addressee> entry : addresseeMap.entrySet()) {
        	if (!addrThreads.containsKey(entry.getKey())) {
	        	Thread thread =  new Thread(() -> {
	                while (!Thread.currentThread().isInterrupted()) {
	
	                    ConcurrentLinkedQueue<Msg> queue = messagesMap.get(entry.getKey());
	                    while (!queue.isEmpty()) {
	                    	Msg message = queue.poll();
	                        message.exec(entry.getValue());
	                    }
	                    try {
	                        Thread.sleep(MessageSystem.DEFAULT_STEP_TIME);
	                    } catch (InterruptedException e) {
	                    	Thread.currentThread().interrupt();
	                    }
	                }
	            });
	            thread.start();
	            addrThreads.put(entry.getKey(), thread);
        	}
        }
    }

	public void stop(SocketMsgClient client) {
		Thread t = addrThreads.get(client.getAddress());
		t.interrupt();
		addresseeMap.remove(client);
	}

	@Override
	public Address getAddress() {
		return selfAddress;
	}

	@Override
	public void setAddress(Address address) {
		selfAddress = address;
	}
}
