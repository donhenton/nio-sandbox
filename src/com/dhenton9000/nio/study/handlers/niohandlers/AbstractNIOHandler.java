package com.dhenton9000.nio.study.handlers.niohandlers;

import com.dhenton9000.nio.study.handlers.Handler;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.Map;
import java.util.Queue;

public abstract class AbstractNIOHandler implements Handler<SelectionKey> {

    private final Map<SocketChannel, Queue<ByteBuffer>> pendingData;

    public AbstractNIOHandler(Map<SocketChannel, Queue<ByteBuffer>> pendingData) {
        this.pendingData = pendingData;
    }

   public Map<SocketChannel, Queue<ByteBuffer>> getPendingData() {
        return this.pendingData;
    }

}
