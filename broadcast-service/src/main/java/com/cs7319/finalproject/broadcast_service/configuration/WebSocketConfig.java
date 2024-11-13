package com.cs7319.finalproject.broadcast_service.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import com.cs7319.finalproject.broadcast_service.controllers.SubscribeListener;
import com.cs7319.finalproject.broadcast_service.services.SubscribeService;

// Configures And Establishes Web Socket Handler
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final SubscribeService subscribeService;

    public WebSocketConfig(SubscribeService subscribeService) {
        this.subscribeService = subscribeService;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new SubscribeListener(subscribeService), "/ws/notes").setAllowedOrigins("*");
    }
}