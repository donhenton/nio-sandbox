package com.dhenton9000.nio.study;

import com.dhenton9000.nio.study.handlers.Handler;
import com.dhenton9000.nio.study.handlers.TransmogrifyChannelHandler;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Collection;

/**
 *
 *
 */
public class SingleThreadedPollingNonBlockingServer {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws Exception {

        ServerSocketChannel ssc = ServerSocketChannel.open();
        
        ssc.bind(new InetSocketAddress(8080));
        boolean forever = true;
        ssc.configureBlocking(false);
 

        Handler<SocketChannel> handler = new TransmogrifyChannelHandler();
        Collection<SocketChannel> sockets = new ArrayList();
        System.out.println("Starting "+ SingleThreadedPollingNonBlockingServer.class.getName());
        while (forever) {
            SocketChannel sc = ssc.accept(); //almost always null;

            if (sc != null) {
                sockets.add(sc);
                System.out.println("Connected to " + sc);
                sc.configureBlocking(false);
            }
            for (SocketChannel socket : sockets) {
                if (socket.isConnected()) {
                    try {
                        handler.handle(socket);
                    } catch (InterruptedException interruptedException) {
                        System.out.println("interrupted blah blah");
                    }
                }
            }
            sockets.removeIf(socket -> !socket.isConnected());

        }

    }

}
