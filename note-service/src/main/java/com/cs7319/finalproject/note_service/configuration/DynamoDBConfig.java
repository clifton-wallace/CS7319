package com.cs7319.finalproject.note_service.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

// Configuration Class For Connecting To Dynamo DB
// I Like Cosmos Better
// NOTE: Use Should Not Use Access Key and Secrets For Real Applications
// This Was Done For Expediancy For This Project
@Configuration
public class DynamoDBConfig {

    // Load from application.properties
    @Value("${aws.accessKey}")
    private String accessKey;

    @Value("${aws.secretKey}")
    private String secretKey;

    @Value("${aws.region}")
    private String region;

    // Build DB Client Based On Settings
    @Bean
    public DynamoDbClient amazonDynamoDB() {

        AwsBasicCredentials awsCreds = AwsBasicCredentials.create(accessKey, secretKey);

        return DynamoDbClient.builder()
                .credentialsProvider(StaticCredentialsProvider.create(awsCreds))
                .region(Region.of(region))
                .build();
    }

    // Build New (V2) Client From Older Client
    @Bean
    public DynamoDbEnhancedClient dynamoDbEnhancedClient(DynamoDbClient dynamoDbClient) {
        return DynamoDbEnhancedClient.builder()
                .dynamoDbClient(dynamoDbClient)
                .build();
    }
}