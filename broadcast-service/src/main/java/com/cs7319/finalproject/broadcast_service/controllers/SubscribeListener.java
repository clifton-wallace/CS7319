package com.cs7319.finalproject.broadcast_service.controllers;

import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.cs7319.finalproject.broadcast_service.services.SubscribeService;

// Listener Which Client Connects To In Order To Receive Updates
public class SubscribeListener extends TextWebSocketHandler {

    private final SubscribeService subscribeService;

    public SubscribeListener(SubscribeService subscribeService) {
        this.subscribeService = subscribeService;
    }

    // Pass Session To Subscribe Service So That Updates Can Be Sent To Client
    @Override
    public void afterConnectionEstablished(@SuppressWarnings("null") WebSocketSession session) throws Exception {
        subscribeService.addSession(session);
    }

    // Remove Session From Subscribe Service So Updates Are No Longer Sent
    @Override
    public void afterConnectionClosed(@SuppressWarnings("null") WebSocketSession session, @SuppressWarnings("null") org.springframework.web.socket.CloseStatus status) throws Exception {
        subscribeService.removeSession(session);
    }
}