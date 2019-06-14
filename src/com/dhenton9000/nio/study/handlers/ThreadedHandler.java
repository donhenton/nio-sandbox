package com.dhenton9000.nio.study.handlers;


public class ThreadedHandler<S> extends UncheckedIOExceptionConverterHandler<S> {

    public ThreadedHandler(Handler<S> s) {
        super(s);
    }

    @Override
    public void handle(S s) {

        new Thread(() -> super.handle(s)).start();

    }


}
