package services;

import javax.websocket.server.ServerEndpoint;
import javax.websocket.OnMessage;

@ServerEndpoint("/echo")
public class ServiceEcho {
     @OnMessage
     public String echo(String message){
        return "Merci pour ton message : " + message;
    }
}
