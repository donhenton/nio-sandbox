package com.dhenton9000.nio.study.handlers.http;

import com.dhenton9000.nio.study.handlers.Handler;
import com.dhenton9000.nio.study.handlers.http.builders.HttpRequestBuilder;
import com.dhenton9000.nio.study.handlers.http.builders.HttpRequestHandler;
import com.dhenton9000.nio.study.handlers.http.builders.HttpResponse;
import com.dhenton9000.nio.study.handlers.http.builders.HttpResponseBuilder;
import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import com.dhenton9000.nio.study.handlers.http.builders.HttpRequest;

public class HttpWriteHandler implements Handler<SelectionKey> {

    @Override
    public void handle(SelectionKey selectionKey) throws InterruptedException, IOException {

        System.out.println("Writing to selection key");
        // get the httpRequestHandler
        HttpRequestHandler httpRequestHandler = (HttpRequestHandler) selectionKey.attachment();
        if (httpRequestHandler == null) {
            System.out.println("Not writing");
            throw new IOException("Can't write");
        }
        System.out.println("Handler " + httpRequestHandler);
        StringBuilder stringBuilder = httpRequestHandler.getReadLines();
        String raw = stringBuilder.toString();
        // build request
        HttpRequest request = new HttpRequestBuilder(raw).build();
        
        System.out.println(request.getHeaders());
        
        //TODO use the various properties of request to read from the file system
        
        System.out.println("location "+request.getLocation());
        
        
        
        
        HttpResponse httpResponse = new HttpResponseBuilder().build();
        httpResponse.setContent("Hello World!".getBytes());
        SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
        httpRequestHandler.sendResponse(socketChannel, httpResponse);
        // read the next round.
        selectionKey.interestOps(SelectionKey.OP_READ);
        System.out.println("Wrote to selection key");

    }

}
