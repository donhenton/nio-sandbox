package com.dhenton9000.nio.study.handlers.http;

import com.dhenton9000.nio.study.handlers.Handler;
import com.dhenton9000.nio.study.handlers.http.builders.HttpRequestHandler;
import com.dhenton9000.nio.study.handlers.http.builders.HttpRequestHandlerBuilder;
import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

public class HttpReadHandler implements Handler<SelectionKey> {

    @Override
    public void handle(SelectionKey selectionKey) throws InterruptedException, IOException {

        HttpRequestHandler httpRequestHandler = (HttpRequestHandler) selectionKey.attachment();
        System.out.println("Handler  " + httpRequestHandler);
        // create it if it doesn't exist
        if (httpRequestHandler == null) {
            System.out.println("Creating Handler");
            httpRequestHandler = new HttpRequestHandlerBuilder().build();
            selectionKey.attach(httpRequestHandler);
        }
        httpRequestHandler = (HttpRequestHandler) selectionKey.attachment();
        System.out.println("Handler  " + httpRequestHandler);
        SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
// get more data
        try {
            httpRequestHandler.read(socketChannel);
        } catch (IOException e) {
            // nothing to read
            socketChannel.close();
            selectionKey.cancel();
        }
        // decode the message
        String line;
        while ((line = httpRequestHandler.line()) != null) {
            // check if we have got everything
            if (!(line == null || line.isEmpty())) {
           
                 continue;
            }
            System.out.println("line '"+line+"' "  );
            // write the next round
            selectionKey.interestOps(SelectionKey.OP_WRITE);
        }
        System.out.println("Read selection key");
    }

}
