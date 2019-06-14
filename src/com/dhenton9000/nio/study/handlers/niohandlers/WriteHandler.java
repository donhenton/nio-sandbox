 

package com.dhenton9000.nio.study.handlers.niohandlers;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.Map;
import java.util.Queue;

 
public class WriteHandler extends AbstractNIOHandler{

    public WriteHandler(Map<SocketChannel, Queue<ByteBuffer>> pendingData) {
        super(pendingData);
    }

    @Override
    public void handle(SelectionKey selectionKey) throws InterruptedException, IOException {
        
        System.out.println("in write handler");
        SocketChannel sc = (SocketChannel) selectionKey.channel();
        Queue<ByteBuffer> queue = getPendingData().get(sc);
        while (!queue.isEmpty()) {
            ByteBuffer buf = queue.peek();
            int written = sc.write(buf);
            if (written == -1) {
                sc.close();
                getPendingData().remove(sc);
                 System.out.println("Disconnected from "+sc+" in write");
                return;
            }
            if (buf.hasRemaining())  return;
            queue.remove();            
        }
        selectionKey.interestOps(SelectionKey.OP_READ);
        
    }

}
