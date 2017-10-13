package ru.otus.bvd.app;

import java.io.IOException;

import ru.otus.bvd.messagesystem.Message;

/**
 * Created by tully.
 */
public interface MsgClient {
    void send(Message msg);

    Message pool();

    Message take() throws InterruptedException;

    void close() throws IOException;
}
