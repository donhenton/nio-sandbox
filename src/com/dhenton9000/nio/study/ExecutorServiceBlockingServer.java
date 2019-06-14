package com.dhenton9000.nio.study;

import com.dhenton9000.nio.study.handlers.PrintingHandler;
import com.dhenton9000.nio.study.handlers.ExecutorServiceHandler;
import com.dhenton9000.nio.study.handlers.TransmogrifyHandler;
import java.net.ServerSocket;
import java.net.Socket;
import com.dhenton9000.nio.study.handlers.Handler;
import java.util.concurrent.Executors;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 *
 *
 */
public class ExecutorServiceBlockingServer {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws Exception {

        ServerSocket ss = new ServerSocket(8080);
        boolean forever = true;

        ThreadPoolExecutor poolItem = new ThreadPoolExecutor(0, Integer.MAX_VALUE,
                60L, TimeUnit.SECONDS,
                new SynchronousQueue<>());

        Handler<Socket> k
                = new ExecutorServiceHandler(
                        new PrintingHandler(
                                new TransmogrifyHandler()
                        ),
                        poolItem, (t, e) -> System.out.println("thread " + t + " exception " + e));

        while (forever) {
            Socket s = ss.accept();

            try {
                k.handle(s);
            } catch (InterruptedException interruptedException) {
                System.out.println("interrupted blah blah");
            }

        }

    }

}
