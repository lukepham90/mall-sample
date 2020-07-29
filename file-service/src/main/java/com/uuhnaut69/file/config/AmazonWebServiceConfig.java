package com.uuhnaut69.file.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author uuhnaut
 * @project mall
 */
@Configuration
public class AmazonWebServiceConfig {

    @Value("${amazon.accesskey}")
    private String amazonAWSAccessKey;

    @Value("${amazon.secretkey}")
    private String amazonAWSSecretKey;

    @Value("${amazon.region}")
    private String amazonRegion;

    public AWSCredentialsProvider amazonAWSCredentialsProvider() {
        return new AWSStaticCredentialsProvider(amazonAWSCredentials());
    }

    @Bean
    public AWSCredentials amazonAWSCredentials() {
        return new BasicAWSCredentials(amazonAWSAccessKey, amazonAWSSecretKey);
    }

    @Bean
    public AmazonS3 amazonS3Client() {
        return AmazonS3ClientBuilder.standard()
                .withCredentials(amazonAWSCredentialsProvider())
                .withRegion(amazonRegion)
                .build();
    }
}
