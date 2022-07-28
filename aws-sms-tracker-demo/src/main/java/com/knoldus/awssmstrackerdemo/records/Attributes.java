package com.knoldus.awssmstrackerdemo.records;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Attributes {

    private String recordStatus;
    private String messageId;
    private String destination;

    public Attributes() {}

    public Attributes(@JsonProperty("record_status") String recordStatus, @JsonProperty("message_id") String messageId) {
        this.recordStatus = recordStatus;
        this.messageId = messageId;
    }

    public String getRecordStatus() {
        return recordStatus;
    }

    public String getMessageId() {
        return messageId;
    }

    @Override
    public String toString() {
        return String.format(recordStatus);
    }
}
