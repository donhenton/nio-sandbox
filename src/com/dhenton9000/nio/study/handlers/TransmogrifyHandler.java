package com.dhenton9000.nio.study.handlers;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UncheckedIOException;
import java.net.Socket;

public class TransmogrifyHandler implements Handler<Socket> {

    @Override
    public void handle(Socket s) throws InterruptedException, IOException {
        try (
                s;
                InputStream in = s.getInputStream();
                OutputStream out = s.getOutputStream();) {
            int data;
            while ((data = in.read()) != -1 && !Thread.currentThread().isInterrupted()) {
                String t = Character.toString((char) data);
                if (t.equals("!")) {
                    throw new IOException("Bang you're dead!");
                }
                if (t.equals("@")) {
                    throw new InterruptedException("adiaos");
                }

                out.write(Util.transmogrify(data));
            }

        }

    }

 

}
