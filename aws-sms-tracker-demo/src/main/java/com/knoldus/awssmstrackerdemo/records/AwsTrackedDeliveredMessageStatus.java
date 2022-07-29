package com.knoldus.awssmstrackerdemo.records;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.mongodb.core.mapping.Document;


import java.io.IOException;

public class AwsTrackedDeliveredMessageStatus {

    private final static ObjectMapper JSON = new ObjectMapper();
    static {
        JSON.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    private Attributes attributes;

    public AwsTrackedDeliveredMessageStatus() {}

    public AwsTrackedDeliveredMessageStatus(Attributes attributes) {
        this.attributes = attributes;
    }

    public Attributes getAttributes() {
        return attributes;
    }

    public static AwsTrackedDeliveredMessageStatus mapJsonToBytes(byte[] message) throws IOException, IOException {
        return JSON.readValue(message, AwsTrackedDeliveredMessageStatus.class);
    }

}