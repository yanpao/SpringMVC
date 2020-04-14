package com.yanpao.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

public class WebSocketConfig implements WebSocketConfigurer {

    /**
     * mapping the WebSocket handler to a specific URL
     */
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(myHandler(), "/echoMessage");
        //.addInterceptors(new HttpSessionHandshakeInterceptor());// pass HTTP session attributes to the WebSocket session
    }
    public WebSocketHandler myHandler() {
        return new MyWebSocketHandler();
    }

    public HandshakeInterceptor myInterceptor(){return new HandshakeInterceptor();}
}
