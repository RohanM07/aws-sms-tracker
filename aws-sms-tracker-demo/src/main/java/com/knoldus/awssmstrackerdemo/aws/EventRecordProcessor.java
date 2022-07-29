package com.knoldus.awssmstrackerdemo.aws;

import com.knoldus.awssmstrackerdemo.service.EventService;
import org.springframework.stereotype.Component;
import software.amazon.kinesis.processor.ShardRecordProcessor;
import software.amazon.kinesis.processor.ShardRecordProcessorFactory;

//Here we defined the SharedRecordProcessorFactory for getting the Stream.
@Component
public class EventRecordProcessor implements ShardRecordProcessorFactory{

    private final EventService eventService;

    public EventRecordProcessor(EventService eventService) {
        this.eventService = eventService;
    }

    public ShardRecordProcessor shardRecordProcessor() {
            return new ConsumereventProcess(eventService);
        }
    }

