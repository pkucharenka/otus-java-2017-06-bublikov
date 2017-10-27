package ru.otus.bvd.ms.app;

import java.io.IOException;

import ru.otus.bvd.ms.core.Addressee;

/**
 * Created by tully.
 */
public interface MsgClient extends Addressee {
    void send(Msg msg);

    Msg pool();

    Msg take() throws InterruptedException;

    void close() throws IOException;
}
