package com.cs7319.finalproject.broadcast_service.services;

import java.util.concurrent.CopyOnWriteArraySet;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.DeleteMessageRequest;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageRequest;

// Service Connects To SQS Queue, Retrieves Items And Publishes Them To
// All Subscribed Clients
@Service
public class SubscribeService {

    private final SqsClient sqsClient;
    private final String queueUrl;
    private final CopyOnWriteArraySet<WebSocketSession> sessions = new CopyOnWriteArraySet<>();

    public SubscribeService(SqsClient sqsClient, @Value("${aws.sqsQueueUrl}") String queueUrl) {
        this.sqsClient = sqsClient;
        this.queueUrl = queueUrl;
    }

    // Poll Queue Every 5 Seconds For Updates
    @Scheduled(fixedRate = 5000) 
    public void pollQueue() {
        ReceiveMessageRequest request = ReceiveMessageRequest.builder()
                .queueUrl(queueUrl)
                .maxNumberOfMessages(10)
                .build();

        sqsClient.receiveMessage(request).messages().forEach(message -> {
            try {
                // Broadcast / Publish
                broadcastMessage(message.body());

                // Remove From Queue
                DeleteMessageRequest deleteRequest = DeleteMessageRequest.builder()
                        .queueUrl(queueUrl)
                        .receiptHandle(message.receiptHandle())
                        .build();
                sqsClient.deleteMessage(deleteRequest);

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void addSession(WebSocketSession session) {
        sessions.add(session);
    }

    public void removeSession(WebSocketSession session) {
        sessions.remove(session);
    }

    private void broadcastMessage(String message) {
        for (WebSocketSession session : sessions) {
            try {
                if (session.isOpen()) {
                    session.sendMessage(new TextMessage(message));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
