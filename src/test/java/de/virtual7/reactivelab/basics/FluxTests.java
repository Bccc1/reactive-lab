package de.virtual7.reactivelab.basics;

import de.virtual7.reactivelab.event.TrackingEvent;
import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Created by mihai.dobrescu
 */
public class FluxTests {

    @Test
    public void testCreateFluxJust() {
        //TODO: create a Flux using Flux.just
        Flux.just("Hello World");
    }

    @Test
    public void testCreateFluxFromList() {
        //TODO: create a Flux from a List
        Flux<String> stringFlux = Flux.fromIterable(Arrays.asList("Hello", "World", "!"));
        stringFlux.log().blockLast();
    }

    @Test
    public void testFluxCountElements() {
        //TODO: count the elements from a flux
        Flux<String> stringFlux = Flux.fromIterable(Arrays.asList("Hello", "World", "!"));
        Mono<Long> count = stringFlux.count();
        System.out.println("Count is " + count.block());
    }

    @Test
    public void testFluxRange() {
        //TODO: create a Flux using the .range operator. Possible usecases?
        Flux<Integer> range = Flux.range(42, 1337);
        range.subscribe(i -> System.out.println(i));
    }

    @Test
    public void testCreateFluxUsingGenerate() throws InterruptedException {
        //TODO: create a Flux using Flux.generate()
        Flux<Object> objectFlux = Flux.generate(() -> 0,
                (state, sink) -> {
                    sink.next(state * 2);
                    if (state >= 42) sink.complete();
                    return state + 1;
                });
        objectFlux.subscribe(i -> System.out.println(i));
    }

    @Test
    public void testCreateFluxUsingInterval() throws InterruptedException {
        //TODO: create a Flux using Flux.interval()
        Flux<Long> interval = Flux.interval(Duration.ofSeconds(1));
        interval.subscribe(System.out::println);
        Thread.sleep(10000);
    }

    @Test
    public void testCreateFluxFromStream() {
        //TODO: create a Flux using Flux.fromStream()
        Flux<Integer> integerFlux = Flux.fromStream(IntStream.range(1, 10).boxed());
        integerFlux.subscribe(System.out::println);
    }

    @Test
    public void testZipThem() throws InterruptedException {
        //TODO: create two Flux instances, one using fromStream() one using .interval with the duration of a second
        //zip them and observe the results
        Flux<Long> interval = Flux.interval(Duration.ofSeconds(1));
        Flux<Integer> stream = Flux.fromStream(IntStream.range(0, 10).boxed());
        interval.zipWith(stream).subscribe(System.out::println);
        Thread.sleep(10000);
    }

    @Test
    public void testSwitchIfEmpty() {
        //TODO: create a Flux instance of your choice and make sure it's empty. Call the switchIfEmpty method on it to supply a fallback
        Flux.empty().switchIfEmpty(Flux.just("fallback for empty")).subscribe(System.out::println);
    }

    @Test
    public void testHandleAndSkipNulls() {
        //TODO: create a Flux using just and for each value call a method which could return a null
        //use handle() to filter out the nulls
        Flux.just(1,2,3,4,5)
                .flatMap(i -> Math.random() > 0.5 ? Flux.just(i) : Flux.empty()) //could't do the null, used Flux.empty() instead
                .handle((i, sink) -> {
                    if (i != null)
                        sink.next(i);
                })
                .subscribe(System.out::println);
    }

    @Test
    public void testFluxOnErrorResume() throws InterruptedException {
        //TODO: generate a Flux using range and then throw an exception for one of the elements. Use onErrorResume as fallback
        Flux.range(0,10)
                .map(i -> i!=5 ? i : null)
                .onErrorResume(e -> Flux.just(42))
                .subscribe(System.out::println);
    }

}
