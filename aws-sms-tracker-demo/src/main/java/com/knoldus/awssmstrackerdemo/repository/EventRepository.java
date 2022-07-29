package com.knoldus.awssmstrackerdemo.repository;

import com.knoldus.awssmstrackerdemo.model.Event;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends MongoRepository<Event, String> {

}