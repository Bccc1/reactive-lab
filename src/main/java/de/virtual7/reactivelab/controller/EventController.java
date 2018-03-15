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
        Flux<TrackingEvent> flux = Flux.interval(Duration.ofSeconds(1)).map(i -> generateEventWithRandomId());
        return flux;
        //return null;
    }

    @GetMapping(path = "/latest", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<TrackingEvent> getEventsLatest() {
        logger.info("getEventsLatest() called!");
        return Flux.range(1,100).map(
                i -> {
                    return new TrackingEvent(new Long(i),
                            TrackingEventType.getRandomType(),
                            BigDecimal.TEN,
                            Instant.now());
                }
        );
    }

    @GetMapping(path = "/top")
    public Flux<TrackingEvent> getTopEvents() {
        logger.info("getTopEvents() called!");

        return null;
    }

    public Flux<TrackingEvent> generateEventFluxWithRandomId() {
        return Flux.just(generateEventWithRandomId());
    }

    public TrackingEvent generateEventWithRandomId() {
        TrackingEvent event = new TrackingEvent();
        event.setEventID((long) (Math.random()*123));
        event.setEventType(TrackingEventType.getRandomType());
        event.setEventValue(new BigDecimal(Math.random()*1234));
        //event.setEventInstant();
        return event;
    }


    @GetMapping(path = "/{eventId}")
    public Mono<TrackingEvent> getEvent(@PathVariable String eventId) {
        return null;
    }

    @GetMapping(path="/freemarker")
    public String freemarker(){
        return "freemarker";
    }
}
