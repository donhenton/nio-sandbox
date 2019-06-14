package com.dhenton9000.nio.study.handlers;

import java.io.IOException;

public class PrintingHandler<S> extends DecoratedHandler<S> {

    public PrintingHandler(Handler<S> s) {
        super(s);
    }

    @Override
    public void handle(S s) throws InterruptedException, IOException {
        System.out.println("Connected to "+ s);
        try {
            super.handle(s);
        }finally {
            System.out.println("Disconnected from "+s);
        }
        
    }

}
