 

package com.dhenton9000.nio.study.handlers;

import java.io.IOException;
import java.net.Socket;

 
public class NastyChump {

    public static void main(String[] args) throws IOException,InterruptedException {
        Socket[] socket = new Socket[3000];
        for (int i=0;i< socket.length;i++) {
            socket[i] = new Socket("localhost",8080);
            System.out.println("NastyChump "+i);
        }
        Thread.sleep(100_000);
    }
}
