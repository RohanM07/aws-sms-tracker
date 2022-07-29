package com.knoldus.awssmstrackerdemo.service;

import com.knoldus.awssmstrackerdemo.model.Event;
import com.knoldus.awssmstrackerdemo.repository.EventRepository;
import org.springframework.stereotype.Service;

//This Class is for service created method to save the record into the mongo repo.
@Service
public class EventService {

    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public void addRecord(Event event){
        eventRepository.save(event);
    }
}
