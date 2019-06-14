 

package com.dhenton9000.nio.study.handlers;

import java.io.IOException;

 
public abstract class DecoratedHandler<S> implements Handler<S> {

    private final Handler<S> handler;

    public DecoratedHandler(Handler<S> s) {
        this.handler = s;
    }
    
    
    @Override
    public void handle(S s) throws InterruptedException, IOException {
        
      this.handler.handle(s);
        
    }

}
