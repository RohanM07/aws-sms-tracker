package com.knoldus.awssmstrackerdemo.aws;

import com.knoldus.awssmstrackerdemo.model.Event;
import com.knoldus.awssmstrackerdemo.records.AwsTrackedDeliveredMessageStatus;
import com.knoldus.awssmstrackerdemo.service.EventService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import software.amazon.kinesis.exceptions.InvalidStateException;
import software.amazon.kinesis.exceptions.ShutdownException;
import software.amazon.kinesis.lifecycle.events.*;
import software.amazon.kinesis.processor.ShardRecordProcessor;
import software.amazon.kinesis.retrieval.KinesisClientRecord;
import java.io.IOException;

//This class is used for actual Processing the records.
public class ConsumereventProcess implements ShardRecordProcessor {

    private static final String SHARD_ID_MDC_KEY = "ShardId";

    private static final Logger log = LoggerFactory.getLogger(ConsumereventProcess.class);

    private String shardId;

    private final EventService eventService;

    public ConsumereventProcess(EventService eventService) {
        this.eventService = eventService;
    }

    public void initialize(InitializationInput initializationInput) {
        shardId = initializationInput.shardId();
        MDC.put(SHARD_ID_MDC_KEY, shardId);
        try {
            log.info("Initializing @ Sequence: {}", initializationInput.extendedSequenceNumber());
        } finally {
            MDC.remove(SHARD_ID_MDC_KEY);
        }
    }

    public void processRecords(ProcessRecordsInput processRecordsInput) {
        MDC.put(SHARD_ID_MDC_KEY, shardId);
        try {
            log.info("Processing {} record(s)", processRecordsInput.records().size());
            processRecordsInput.records().forEach(incomingDeliveryStatus -> {
                try {
                    processingRecord(incomingDeliveryStatus);
                } catch (IOException e) {
                    log.info("Failed to process records.");}
            });
        } catch (Throwable t) {
            log.error("Caught throwable while processing records. Aborting.");
            Runtime.getRuntime().halt(1);
        } finally {
            MDC.remove(SHARD_ID_MDC_KEY);
        }
    }
    public void leaseLost(LeaseLostInput leaseLostInput) {
        MDC.put(SHARD_ID_MDC_KEY, shardId);
        try {
            log.info("Lost lease, so terminating.");
        } finally {
            MDC.remove(SHARD_ID_MDC_KEY);
        }
    }

    public void shardEnded(ShardEndedInput shardEndedInput) {
        MDC.put(SHARD_ID_MDC_KEY, shardId);
        try {
            log.info("Reached shard end checkpointing.");
            shardEndedInput.checkpointer().checkpoint();
        } catch (ShutdownException | InvalidStateException e) {
            log.error("Exception while checkpointing at shard end. Giving up.", e);
        } finally {
            MDC.remove(SHARD_ID_MDC_KEY);
        }
    }

    public void shutdownRequested(ShutdownRequestedInput shutdownRequestedInput) {
        MDC.put(SHARD_ID_MDC_KEY, shardId);
        try {
            log.info("Scheduler is shutting down, checkpointing.");
            shutdownRequestedInput.checkpointer().checkpoint();
        } catch (ShutdownException | InvalidStateException e) {
            log.error("Exception while checkpointing at requested shutdown. Giving up.", e);
        } finally {
            MDC.remove(SHARD_ID_MDC_KEY);
        }
    }
    public void processingRecord(KinesisClientRecord record) throws IOException {

        byte[] messageStatus = new byte[record.data().remaining()];

        record.data().get(messageStatus);
        log.info("Record Data Processing checking ", record.data().get(messageStatus));
        log.info("Record Process Data length ", messageStatus.length);
        AwsTrackedDeliveredMessageStatus awsTrackedDeliveredMessageStatus = AwsTrackedDeliveredMessageStatus.mapJsonToBytes(messageStatus);
        log.info("Object Mapping JSON check ", awsTrackedDeliveredMessageStatus.getAttributes().getRecordStatus());
        Event event = new Event(awsTrackedDeliveredMessageStatus.getAttributes().getRecordStatus(),
                awsTrackedDeliveredMessageStatus.getAttributes().getMessageId(),
                awsTrackedDeliveredMessageStatus.getAttributes().getDestinationMobileNumber());
        log.info("Sending event to the mongoDb ",event);
        log.info("Checking the message status ",event.getRecordStatus());
        eventService.addRecord(event);
        log.info("Adding the record data in MongoDb through the service ",event.hashCode());
    }
}
