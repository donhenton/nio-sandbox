package com.dhenton9000.nio.study;

import com.dhenton9000.nio.study.handlers.Handler;
import com.dhenton9000.nio.study.handlers.PrintingHandler;
import com.dhenton9000.nio.study.handlers.TransmogrifyHandler;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 *
 */
public class SingleThreadBlockingServer {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {

        ServerSocket ss = new ServerSocket(8080);
        boolean forever = true;
        while (forever) {
            Socket s = ss.accept();

            Handler<Socket> k
                    = new PrintingHandler(
                            new TransmogrifyHandler()
                    );

            try {
                k.handle(s);
            } catch (IOException | InterruptedException ex) {
                System.out.println("server stuff error "+ex.getMessage());
            }

        }

    }

}
