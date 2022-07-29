package com.knoldus.awssmstrackerdemo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Event")
public class Event {
    @Id
    private String recordStatus;
    private String messageId;
    private String destinationMobileNumber;

    public Event(@JsonProperty("recordStatus") String recordStatus,
                  @JsonProperty("messageId") String messageId,
                 @JsonProperty("destinationMobileNumber") String destinationMobileNumber) {
        this.recordStatus = recordStatus;
        this.messageId = messageId;
        this.destinationMobileNumber= destinationMobileNumber;
    }

    public String getRecordStatus() {
        return recordStatus;
    }

    public void setRecordStatus(String recordStatus) {
        this.recordStatus = recordStatus;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getDestinationMobileNumber() {
        return destinationMobileNumber;
    }

    public void setDestinationMobileNumber(String destinationMobileNumber) {
        this.destinationMobileNumber = destinationMobileNumber;
    }
}