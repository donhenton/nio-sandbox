package com.dhenton9000.nio.study;

import com.dhenton9000.nio.study.handlers.BlockingChannelHandler;
import com.dhenton9000.nio.study.handlers.PrintingHandler;
import com.dhenton9000.nio.study.handlers.ExecutorServiceHandler;
import com.dhenton9000.nio.study.handlers.TransmogrifyHandler;
import com.dhenton9000.nio.study.handlers.Handler;
import com.dhenton9000.nio.study.handlers.TransmogrifyChannelHandler;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 *
 *
 */
public class BlockingNIOServer {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws Exception {

        ServerSocketChannel ssc = ServerSocketChannel.open();
        
        ssc.bind(new InetSocketAddress(8080));
        boolean forever = true;

        ThreadPoolExecutor poolItem = new ThreadPoolExecutor(0, Integer.MAX_VALUE,
                60L, TimeUnit.SECONDS,
                new SynchronousQueue<>());

        Handler<SocketChannel> k
                = new ExecutorServiceHandler(
                        new PrintingHandler(
                                new BlockingChannelHandler( 
                                new TransmogrifyChannelHandler()
                                        )
                        ),
                        poolItem, (t, e) -> System.out.println("thread " + t + " exception " + e));

        while (forever) {
            SocketChannel s = ssc.accept();

            try {
                k.handle(s);
            } catch (InterruptedException interruptedException) {
                System.out.println("interrupted blah blah");
            }

        }

    }

}
