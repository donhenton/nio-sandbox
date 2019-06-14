/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dhenton9000.nio.study.handlers;

import java.io.IOException;

/**
 *
 * @author dhenton
 * @param <S> the item that gets handled
 * 
 */
public interface Handler<S>  {
    
    public void handle(S s) throws InterruptedException,IOException;
    
}
