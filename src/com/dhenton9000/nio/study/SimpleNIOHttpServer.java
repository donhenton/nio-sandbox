package com.dhenton9000.nio.study;

import com.dhenton9000.nio.study.handlers.http.HttpAcceptHandler;
import com.dhenton9000.nio.study.handlers.Handler;
import com.dhenton9000.nio.study.handlers.http.HttpReadHandler;
import com.dhenton9000.nio.study.handlers.http.HttpWriteHandler;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

/**
 * https://github.com/jccarrillo/java-nio-http-server
 *
 */
public class SimpleNIOHttpServer {

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

        Map<SocketChannel, Queue<ByteBuffer>> pendingData = new HashMap<>();
        Handler<SelectionKey> acceptHandler = new HttpAcceptHandler(selector);
        Handler<SelectionKey> readHandler = new HttpReadHandler();
        Handler<SelectionKey> writeHandler = new HttpWriteHandler();
        Collection<SocketChannel> sockets = new ArrayList();
        System.out.println("Starting " + SingleThreadedSelectorNonBlockingServer.class.getName());
        while (forever) {
            selector.select();
            System.out.println("dropping out of loop");
            Set<SelectionKey> keys = selector.selectedKeys();
            for (Iterator<SelectionKey> it = keys.iterator(); it.hasNext();) {
                SelectionKey key = it.next();
               
                it.remove();
                if (key.isValid()) {
                    if (key.isAcceptable()) {
                        System.out.println("accept found");
                        acceptHandler.handle(key);
                    } else if (key.isReadable()) {
                         System.out.println("read found");
                        readHandler.handle(key);
                    } else if (key.isWritable()) {
                         System.out.println("write found");
                        writeHandler.handle(key);
                    }
                }
            }

        }
    }
}
