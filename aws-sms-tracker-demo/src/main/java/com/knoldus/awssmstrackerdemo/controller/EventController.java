package com.knoldus.awssmstrackerdemo.controller;

import com.knoldus.awssmstrackerdemo.model.Event;
import com.knoldus.awssmstrackerdemo.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class EventController {
    @Autowired
    EventRepository eventRepository;

    @GetMapping("/event")
    public ResponseEntity<?> getStudent(@RequestBody Event event){
        return ResponseEntity.ok(this.eventRepository.findAll());
    }
}
