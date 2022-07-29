package com.knoldus.awssmstrackerdemo.records;
import com.fasterxml.jackson.annotation.JsonProperty;


public class Attributes {

    private String recordStatus;
    private String messageId;
    private String destinationMobileNumber;

    public Attributes() {}

    public Attributes(@JsonProperty("record_status") String recordStatus, @JsonProperty("message_id") String messageId, @JsonProperty("destination_phone_number") String destinationMobileNumber) {
        this.recordStatus = recordStatus;
        this.messageId = messageId;
        this.destinationMobileNumber = destinationMobileNumber;
    }

    public String getRecordStatus() {
        return recordStatus;
    }

    public String getMessageId() {
        return messageId;
    }

    public String getDestinationMobileNumber() {
        return destinationMobileNumber;
    }

    @Override
    public String toString() {
        return String.format(recordStatus);
    }
}
