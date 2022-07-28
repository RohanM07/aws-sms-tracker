package com.knoldus.awssmstrackerdemo.processing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AwsSmsTrackerDemoApplication {
    public static void main(String... args) {
        SpringApplication.run(AwsSmsTrackerDemoApplication.class,args);
        new EventRecordProcessor().run();
    }
}
