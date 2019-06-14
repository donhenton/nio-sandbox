 

package com.dhenton9000.nio.study.handlers.niohandlers;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
 

 
public class AcceptHandler extends AbstractNIOHandler {

   

    public AcceptHandler(Map<SocketChannel, Queue<ByteBuffer>> pendingData) {
        super(pendingData);
    }

    @Override
    public void handle(SelectionKey s) throws InterruptedException, IOException {
        ServerSocketChannel ssc =     ( ServerSocketChannel) s.channel();
        SocketChannel sc = ssc.accept();
        System.out.println("Connected to "+sc+"Accept method");
        this.getPendingData().put(sc, new ConcurrentLinkedQueue());
        sc.configureBlocking(false);
        sc.register(s.selector(),SelectionKey.OP_READ);
    }

}
