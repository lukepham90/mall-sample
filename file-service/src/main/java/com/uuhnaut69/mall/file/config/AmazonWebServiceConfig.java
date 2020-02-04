package com.uuhnaut69.mall.file.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AmazonWebServiceConfig {

    @Value("${amazon.accesskey}")
    private String amazonAWSAccessKey;

    @Value("${amazon.secretkey}")
    private String amazonAWSSecretKey;

    @Value("${amazon.region}")
    private String amazonRegion;

    @Value("${amazon.s3.bucket-name}")
    private String amazonS3BucketName;

    @Value("${amazon.s3.endpoint}")
    private String amazonS3EndPoint;

    public AWSCredentialsProvider amazonAWSCredentialsProvider() {
        return new AWSStaticCredentialsProvider(amazonAWSCredentials());
    }

    @Bean
    public AWSCredentials amazonAWSCredentials() {
        return new BasicAWSCredentials(amazonAWSAccessKey, amazonAWSSecretKey);
    }

    @Bean
    public AmazonS3 amazonS3Client(AWSCredentials awsCredentials) {
        return AmazonS3ClientBuilder.standard().withCredentials(amazonAWSCredentialsProvider()).withRegion(amazonRegion)
                .build();
    }

    @Bean(name = "amazonS3BucketName")
    public String getAWSS3BucketName() {
        return amazonS3BucketName;
    }

    @Bean(name = "amazonS3EndPoint")
    public String getAWSS3EndPoint() {
        return amazonS3EndPoint;
    }
}
