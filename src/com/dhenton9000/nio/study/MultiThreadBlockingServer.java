package com.dhenton9000.nio.study;

import com.dhenton9000.nio.study.handlers.PrintingHandler;
import com.dhenton9000.nio.study.handlers.ThreadedHandler;
import com.dhenton9000.nio.study.handlers.TransmogrifyHandler;
import java.net.ServerSocket;
import java.net.Socket;
import com.dhenton9000.nio.study.handlers.Handler;

/**
 *
 *
 */
public class MultiThreadBlockingServer {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws  Exception {

        ServerSocket ss = new ServerSocket(8080);
        boolean forever = true;
        while (forever) {
            Socket s = ss.accept();
            
            Handler<Socket> k = 
                    
            new ThreadedHandler(         
                    
                    new PrintingHandler(
                    new TransmogrifyHandler()
            ));
            
            try {
                k.handle(s);
            } catch (InterruptedException interruptedException) {
                System.out.println("interrupted blah blah");
            }

        }

    }
 
 
}
