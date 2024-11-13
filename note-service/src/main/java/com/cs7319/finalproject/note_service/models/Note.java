package com.cs7319.finalproject.note_service.models;

import java.util.List;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

@DynamoDbBean
public class Note {

    private String id;
    private String title;
    private String content;
    private List<NoteVersion> versions;
    private String created;
    private String lastUpdated;

    @DynamoDbAttribute(value = "id")
    @DynamoDbPartitionKey
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<NoteVersion> getVersions() {
        return versions;
    }

    public void setVersions(List<NoteVersion> versions) {
        this.versions = versions;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    @DynamoDbAttribute(value = "lastUpdated")
    public String getLastUpdated() {
        return lastUpdated;
    }

    public void setlastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}
