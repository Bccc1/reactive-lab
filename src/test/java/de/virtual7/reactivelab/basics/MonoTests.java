package de.virtual7.reactivelab.basics;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

/**
 * Created by mihai.dobrescu
 */
public class MonoTests {

    @Test
    public void testCreateVoidMono() {
        //TODO: create an empty Mono
        Mono<Object> empty = Mono.empty();
        empty.log().block();
    }

    @Test
    public void testCreateScalarMono() {
        //TODO: create a Mono with the "Hello World" value
        Mono<String> hello_world = Mono.just("Hello World");
        hello_world.log().block();
    }

    @Test
    public void testFlatMapMono() {
        //TODO: create a Mono with the "Hello" value and append "World" to it using the flatMap operator
        Mono<String> hello = Mono.just("Hello").flatMap(x -> Mono.just(x + " World"));
        hello.log().block();
    }

    @Test
    public void testCreateFluxFromMono() {
        //TODO: Create a Mono from a List and then convert it to a Flux
        List<String> list = Arrays.asList("JAVA", "SAMPLE", "APPROACH", ".COM");
        Mono<List<String>> listMono = Mono.just(list);
        Flux<String> stringFlux = Flux.fromIterable(listMono.block());
        stringFlux.log();
        stringFlux.blockLast();
    }

    @Test
    public void testMergeMonos() {
        //TODO: Create two Mono instances and merge them, what do you get ?
        Mono<String> mono1 = Mono.just("Hello");
        Mono<String> mono2 = Mono.just("World");
        Flux<String> stringFlux = mono1.mergeWith(mono2);
        stringFlux.log().blockLast();
    }

    @Test
    public void testZipMonos() {
        //TODO: Create two Mono instances and zip them
        Mono<String> mono1 = Mono.just("Hello");
        Mono<String> mono2 = Mono.just("World");
        Mono<Tuple2<String, String>> tuple2Mono = mono1.zipWith(mono2);
    }

    @Test
    public void testBlockingMono() {
        //TODO: Create a Mono instance and convert it to a blocking call
        Mono.just("Hello World").log().delaySubscription(Duration.ofMillis(500)).block();
    }
}
