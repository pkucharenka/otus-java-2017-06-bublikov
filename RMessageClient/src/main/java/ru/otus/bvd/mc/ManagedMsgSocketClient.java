package ru.otus.bvd.mc;

import java.io.IOException;
import java.net.Socket;

import ru.otus.bvd.ms.channel.SocketMsgClient;


/**
 * Created by tully.
 */
class ManagedMsgSocketClient extends SocketMsgClient {

    private final Socket socket;

    ManagedMsgSocketClient(String host, int port) throws IOException {
        this(new Socket(host, port));
    }

    private ManagedMsgSocketClient(Socket socket) throws IOException {
        super(socket);
        this.socket = socket;
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
