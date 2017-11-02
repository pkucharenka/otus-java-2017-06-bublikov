package ru.otus.bvd.mc;

import java.io.IOException;
import java.net.Socket;
import java.util.UUID;

import ru.otus.bvd.ms.channel.SocketMsgClient;
import ru.otus.bvd.ms.core.Address;
import ru.otus.bvd.ms.core.AddressGroup;


/**
 * Created by tully.
 */
class ManagedMsgSocketClient extends SocketMsgClient {
    public static final String HOST_DEFAULT = "localhost";
    public static final int PORT_DEFAULT = 5050;

    private final Socket socket;

    ManagedMsgSocketClient(String host, int port, AddressGroup addressGroup) throws IOException {
        this(new Socket(host, port), addressGroup);
    }

    private ManagedMsgSocketClient(Socket socket, AddressGroup addressGroup) throws IOException {
        super(socket, null);
        this.socket = socket;
        setAddress( new Address(addressGroup, UUID.randomUUID().toString()) );
    }

    public void close() {
        super.close();
        try {
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
