package com.knoldus.awssmstrackerdemo.aws;

import org.springframework.stereotype.Component;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.cloudwatch.CloudWatchAsyncClient;
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient;
import software.amazon.awssdk.services.kinesis.KinesisAsyncClient;
import software.amazon.kinesis.common.ConfigsBuilder;
import software.amazon.kinesis.common.KinesisClientUtil;
import software.amazon.kinesis.coordinator.Scheduler;
import software.amazon.kinesis.metrics.NullMetricsFactory;
import java.util.UUID;

//Here the actual run method for the Kinesis records connection and return the stream
@Component
public class AwsConsumerService implements Runnable{

    private final EventRecordProcessor eventRecordProcessor;

    public AwsConsumerService(EventRecordProcessor eventRecordProcessor) {
        this.eventRecordProcessor = eventRecordProcessor;
    }

    public void run() {
        String streamName = "aws-sms-tracker-demo";
        Region region = Region.of("us-east-1");
        DynamoDbAsyncClient dynamoClient = DynamoDbAsyncClient.builder().region(region).build();
        CloudWatchAsyncClient cloudWatchClient = CloudWatchAsyncClient.builder().region(region).build();
        KinesisAsyncClient kinesisClient = KinesisClientUtil.createKinesisAsyncClient(KinesisAsyncClient.builder().region(region));
        ConfigsBuilder configsBuilder = new ConfigsBuilder(streamName,
                "demo"
                , kinesisClient
                , dynamoClient
                , cloudWatchClient
                , UUID.randomUUID().toString()
                ,eventRecordProcessor);
        Scheduler scheduler = new Scheduler(
                configsBuilder.checkpointConfig(),
                configsBuilder.coordinatorConfig(),
                configsBuilder.leaseManagementConfig(),
                configsBuilder.lifecycleConfig(),
                configsBuilder.metricsConfig().metricsFactory(new NullMetricsFactory()),
                configsBuilder.processorConfig(),
                configsBuilder.retrievalConfig()
        );
        scheduler.run();
    }
}
