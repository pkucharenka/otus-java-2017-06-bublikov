package ru.otus.bvd.ms.messages;

import java.util.logging.Logger;

import ru.otus.bvd.ms.core.Address;

/**
 * Created by tully.
 */
public class MsgGetUserByIdAnswer extends MsgToFrontend {
    private static final Logger log = Logger.getLogger(MsgToFrontend.class.getName());
	
	private final String name;
    private final long id;

    public MsgGetUserByIdAnswer(Address from, Address to, long requestId, String name, long id) {
        super(MsgGetUserByIdAnswer.class, from, to, requestId);
        this.name = name;
        this.id = id;
    }

	@Override
	public void execEndPoint() {
		try {
			frontendService.sendUser(id, name, requestId);
		} catch (Exception e) {
			log.severe(e.toString());
		}
	}
}
