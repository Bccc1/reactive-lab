package de.virtual7.reactivelab.controller;

import de.virtual7.reactivelab.event.TrackingEvent;
import de.virtual7.reactivelab.event.TrackingEventType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;

/**
 * Created by mihai.dobrescu
 */
@Controller
@RequestMapping("/events")
public class EventController {

    Logger logger = LoggerFactory.getLogger(EventController.class);

    @GetMapping(path = "")
    public Flux<TrackingEvent> getEvents() {
        logger.info("getEvents() called!");
        Flux<TrackingEvent> flux = Flux
                .interval(Duration.ofSeconds(1))
                .map(i -> generateEventWithRandomId());
        return flux;
    }

    @GetMapping(path = "/latest", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<TrackingEvent> getEventsLatest() {
        logger.info("getEventsLatest() called!");
        return Flux
                .range(1,100)
                .map( i -> generateEventWithId(Long.valueOf(i)) );
    }

    @GetMapping(path = "/top")
    public Flux<TrackingEvent> getTopEvents() {
        logger.info("getTopEvents() called!");

        return null;
    }




    @GetMapping(path = "/{eventId}")
    public Mono<TrackingEvent> getEvent(@PathVariable String eventId) {
        return Mono.just(generateEventWithId(Long.valueOf(eventId)));
    }

    @GetMapping(path="/freemarker")
    public String freemarker(){
        return "freemarker";
    }


    // *
    // * Helper methods
    // *


    private Flux<TrackingEvent> generateEventFluxWithRandomId() {
        return Flux.just(generateEventWithRandomId());
    }

    private TrackingEvent generateEventWithRandomId() {
        return generateEventWithId((long) (Math.random()*123));
    }

    private TrackingEvent generateEventWithId(Long id){
        return new TrackingEvent(new Long(id),
                TrackingEventType.getRandomType(),
                new BigDecimal(Math.random()*1234),
                Instant.now());
    }
}
