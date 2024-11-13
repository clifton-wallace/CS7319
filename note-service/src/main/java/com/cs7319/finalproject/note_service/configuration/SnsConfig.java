package com.cs7319.finalproject.note_service.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sns.SnsClient;

// Configuration Class For Connecting To SNS
// NOTE: Use Should Not Use Access Key and Secrets For Real Applications
// This Was Done For Expediancy For This Project
@Configuration
public class SnsConfig {

    @Value("${aws.snsAccessKey}")
    private String accessKeyId;

    @Value("${aws.snsSecretKey}")
    private String secretAccessKey;

    @Value("${aws.snsRegion}")
    private String region;

    @Bean
    public SnsClient snsClient() {
        AwsBasicCredentials awsCreds = AwsBasicCredentials.create(accessKeyId, secretAccessKey);
        return SnsClient.builder()
                .credentialsProvider(StaticCredentialsProvider.create(awsCreds))
                .region(Region.of(region))
                .build();
    }
}