package com.example.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.applicationdiscovery.model.DataSource;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

@Configuration
public class AwsConfig {
	
//	@Bean
//	public JdbcTemplate jdbcTemplate(DataSource dataSource) {
//	    return new JdbcTemplate(dataSource);
//	}
	


@Value("${aws.access_key_id}")
private String accessKey;

@Value("${aws.secret_access_key}")
private String secretKey;




@Bean
public AmazonS3 amazonS3() {
	final BasicAWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
	return AmazonS3ClientBuilder
	.standard()
    .withRegion("sa-east-1")
    .withCredentials(new AWSStaticCredentialsProvider(credentials))
    .build();
	
}

}
