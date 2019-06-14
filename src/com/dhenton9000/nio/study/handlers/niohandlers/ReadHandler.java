package com.dhenton9000.nio.study.handlers.niohandlers;

import com.dhenton9000.nio.study.handlers.Util;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.Map;
import java.util.Queue;

public class ReadHandler extends AbstractNIOHandler {

    public ReadHandler(Map<SocketChannel, Queue<ByteBuffer>> pendingData) {
        super(pendingData);
    }

    @Override
    public void handle(SelectionKey selectionKey) throws InterruptedException, IOException {

        SocketChannel sc = (SocketChannel) selectionKey.channel();
        System.out.println("in read handler");
        ByteBuffer buf = ByteBuffer.allocateDirect(80);
        int read = sc.read(buf);
        if (read == -1) {
            getPendingData().remove(sc);
            sc.close();
            System.out.println("Disconnected from "+sc+ " in read");
            return;
        }
        if (read > 0) {
            
            Util.transmogrify(buf);
            getPendingData().get(sc).add(buf);
            selectionKey.interestOps(SelectionKey.OP_WRITE);

        }

    }

}
