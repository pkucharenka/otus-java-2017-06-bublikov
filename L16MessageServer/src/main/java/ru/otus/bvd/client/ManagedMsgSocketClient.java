package ru.otus.bvd.client;


import java.io.IOException;
import java.net.Socket;

import ru.otus.bvd.app.SocketMsgClient;

/**
 * Created by tully.
 */
public class ManagedMsgSocketClient extends SocketMsgClient {

    private final Socket socket;

    public ManagedMsgSocketClient(String host, int port) throws IOException {
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
