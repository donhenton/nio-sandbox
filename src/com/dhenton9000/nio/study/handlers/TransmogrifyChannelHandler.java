package com.dhenton9000.nio.study.handlers;

import java.io.IOException;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class TransmogrifyChannelHandler implements Handler<SocketChannel> {

    @Override
    public void handle(SocketChannel sc) throws InterruptedException, IOException {
         ByteBuffer buf = ByteBuffer.allocateDirect(80);
         int read = sc.read(buf);
         if (read == -1) {
             
             sc.close();
             return;
         }
         if ( read > 0) {
             Util.transmogrify(buf);
             while(buf.hasRemaining()) {
                 sc.write(buf);
                 
             }
          //   buf.compact();
             
         }

    }

   
    

   

}
