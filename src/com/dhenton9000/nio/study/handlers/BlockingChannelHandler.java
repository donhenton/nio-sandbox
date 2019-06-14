 

package com.dhenton9000.nio.study.handlers;

import java.io.IOException;
import java.nio.channels.SocketChannel;

 
public class BlockingChannelHandler extends DecoratedHandler<SocketChannel> {

   

    public BlockingChannelHandler(Handler<SocketChannel> s) {
        super(s); 
    }

    @Override
    public void handle(SocketChannel sc) throws InterruptedException, IOException {
        while (sc.isConnected()) {
            
            super.handle(sc);
            
            
            
        }
    }

    
    
    
}
