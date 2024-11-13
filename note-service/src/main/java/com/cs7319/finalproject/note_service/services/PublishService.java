package com.cs7319.finalproject.note_service.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.cs7319.finalproject.note_service.models.Note;
import com.cs7319.finalproject.note_service.models.Action;
import com.cs7319.finalproject.note_service.models.NotePublish;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.PublishRequest;
import software.amazon.awssdk.services.sns.model.PublishResponse;

// Service Publishes Note Updates To SNS For Other Clients To Receive
@Service
public class PublishService {

     // Load from application.properties
    @Value("${aws.topicArn}")
    private String topicArn;

    private final SnsClient snsClient;

    public PublishService(SnsClient snsClient) {
        this.snsClient = snsClient;
    }

    // Publish Message To Queue/Topic
    public String publishMessage(Action action, Note note) throws JsonProcessingException {

        String notePublish = buildPublishMessage(action, note);

        PublishRequest publishRequest = PublishRequest.builder()
                .topicArn(topicArn)
                .message(notePublish)
                .build();

        PublishResponse publishResponse = snsClient.publish(publishRequest);
        return publishResponse.messageId();
    }

    // Create Payload To Publish
    public String buildPublishMessage(Action action, Note note) throws JsonProcessingException{
        NotePublish notePublish = new NotePublish();
        notePublish.setAction(action);
        notePublish.setNote(note);

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = objectMapper.writeValueAsString(notePublish);

        return jsonString;
    }
}
