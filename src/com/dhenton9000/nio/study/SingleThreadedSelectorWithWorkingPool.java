package com.dhenton9000.nio.study;

import com.dhenton9000.nio.study.handlers.niohandlers.PooledReadHandler;
import com.dhenton9000.nio.study.handlers.niohandlers.AcceptHandler;
import com.dhenton9000.nio.study.handlers.Handler;
import com.dhenton9000.nio.study.handlers.niohandlers.WriteHandler;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 *
 */
public class SingleThreadedSelectorWithWorkingPool {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws Exception {

        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.bind(new InetSocketAddress(8080));
        boolean forever = true;
        ssc.configureBlocking(false);
        Selector selector = Selector.open();
        ssc.register(selector, SelectionKey.OP_ACCEPT); //client is connecting
        
        
        ExecutorService pool = Executors.newFixedThreadPool(10);
        Map<SocketChannel,Queue<ByteBuffer>> pendingData = new ConcurrentHashMap<>();
        
        Queue<Runnable> selectorActions = new ConcurrentLinkedQueue<>();
       
        Handler<SelectionKey> acceptHandler = new AcceptHandler(pendingData);
        Handler<SelectionKey> readHandler = new PooledReadHandler(pool,selectorActions,pendingData);
        Handler<SelectionKey> writeHandler = new WriteHandler(pendingData);
        Collection<SocketChannel> sockets = new ArrayList();
        System.out.println("Starting " + SingleThreadedSelectorWithWorkingPool.class.getName());
        while (forever) {
            selector.select();
             
            processSelectorActions(selectorActions);
            
            
            Set<SelectionKey> keys = selector.selectedKeys();
            for (Iterator<SelectionKey> it = keys.iterator(); it.hasNext();) {
                SelectionKey key = it.next();
                it.remove();
                if (key.isValid()) {
                    if (key.isAcceptable()) {
                        acceptHandler.handle(key);
                    } else if (key.isReadable()) {
                        readHandler.handle(key);
                    } else if (key.isWritable()) {
                        writeHandler.handle(key);
                    }
                }
            }

        }

    }

    private static void processSelectorActions(Queue<Runnable> selectorActions) {
         Runnable action;
         while ((action =selectorActions.poll()) != null) {
             action.run();
             
         }
    }

}
