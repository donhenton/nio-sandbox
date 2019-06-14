package com.dhenton9000.nio.study.handlers.niohandlers;

import com.dhenton9000.nio.study.handlers.Handler;
import com.dhenton9000.nio.study.handlers.Util;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ExecutorService;

public class PooledReadHandler extends AbstractNIOHandler {

    private final ExecutorService pool;
    private final Queue<Runnable> selectorActions;

    public PooledReadHandler(ExecutorService pool, Queue<Runnable> selectorActions, Map<SocketChannel, Queue<ByteBuffer>> pendingData) {
        super(pendingData);
        this.pool = pool;
        this.selectorActions = selectorActions;
    }

    @Override
    public void handle(SelectionKey selectionKey) throws InterruptedException, IOException {

        SocketChannel sc = (SocketChannel) selectionKey.channel();
        ByteBuffer buf = ByteBuffer.allocateDirect(80);
        int read = sc.read(buf);
        if (read == -1) {
            getPendingData().remove(sc);
            sc.close();
            System.out.println("Disconnected from " + sc + " in pooled Read");
            return;
        }
        if (read > 0) {

            this.pool.submit(() -> {
                Util.transmogrify(buf);
                getPendingData().get(sc).add(buf);
                selectorActions.add(() -> selectionKey.interestOps(SelectionKey.OP_WRITE));
                selectionKey.selector().wakeup();
            });

            ;

        }

    }

}
