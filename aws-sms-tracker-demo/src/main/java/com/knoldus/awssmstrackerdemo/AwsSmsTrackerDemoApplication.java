package com.knoldus.awssmstrackerdemo;

import com.knoldus.awssmstrackerdemo.aws.AwsConsumerService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AwsSmsTrackerDemoApplication implements ApplicationRunner {
    private final AwsConsumerService awsConsumerService;

    public AwsSmsTrackerDemoApplication(AwsConsumerService awsConsumerService) {
        this.awsConsumerService = awsConsumerService;
    }

    public static void main(String... args) {
        SpringApplication.run(AwsSmsTrackerDemoApplication.class,args);

    }
    @Override
    public void run(ApplicationArguments args) throws Exception {
     awsConsumerService.run();
    }
}
