package com.dhenton9000.nio.study.handlers.http;

import com.dhenton9000.nio.study.handlers.Handler;
import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class HttpAcceptHandler implements Handler<SelectionKey> {

    private final Selector selector;

    public HttpAcceptHandler(Selector selector) {
        this.selector = selector;
    }

    @Override
    public void handle(SelectionKey selectionKey) throws InterruptedException, IOException {
        ServerSocketChannel serverSocketChannel = (ServerSocketChannel) selectionKey.channel();
        SocketChannel socketChannel = serverSocketChannel.accept();
        if (socketChannel == null) {
            System.out.println("Disregard selection key");
            return;
        }
        System.out.println("Accepting selection key");
        socketChannel.configureBlocking(false);
        socketChannel.register(this.selector, SelectionKey.OP_READ);
        System.out.println("Accepted selection key");
    }

}
