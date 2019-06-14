package com.dhenton9000.nio.study.handlers;

import java.io.IOException;
import java.io.UncheckedIOException;

public class UncheckedIOExceptionConverterHandler<S> extends
        DecoratedHandler<S> {

    public UncheckedIOExceptionConverterHandler(Handler<S> s) {
        super(s);
    }

    @Override
    public void handle(S s) {
        try {
            super.handle(s);
        } catch (IOException ex) {
            throw new UncheckedIOException(ex);
        } catch (InterruptedException ie) {
            System.out.println("BYE");

        }
    }

}
