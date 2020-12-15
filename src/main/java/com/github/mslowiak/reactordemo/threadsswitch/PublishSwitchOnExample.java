package com.github.mslowiak.reactordemo.threadsswitch;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

public class PublishSwitchOnExample {

    private static Mono<String> firstOperation(String text) {
        return Mono.just("A" + text)
                .doOnNext(x -> System.out.println("Thread " + Thread.currentThread().getName() + " - for " + x));
    }

    private static Mono<String> secondOperation(String text) {
        return Mono.just("B" + text)
                .doOnNext(x -> System.out.println("Thread " + Thread.currentThread().getName() + " - for " + x));
    }

    private static Mono<String> thirdOperation(String text) {
        return Mono.just("C" + text)
                .doOnNext(x -> System.out.println("Thread " + Thread.currentThread().getName() + " - for " + x));
    }

    private static void mainThread() {
        Flux.just("Aircraft", "Car", "Train")
                .flatMap(PublishSwitchOnExample::firstOperation)
                .flatMap(PublishSwitchOnExample::secondOperation)
                .flatMap(PublishSwitchOnExample::thirdOperation)
                .blockLast();
    }

    private static void subscribeOn() {
        Flux.just("Aircraft", "Car", "Train")
                .flatMap(PublishSwitchOnExample::firstOperation)
                .flatMap(PublishSwitchOnExample::secondOperation)
                .subscribeOn(Schedulers.newBoundedElastic(10, 10, "subscribeOn_thread"))
                .flatMap(PublishSwitchOnExample::thirdOperation)
                .blockLast();
    }

    private static void publishOn() {
        Flux.just("Aircraft", "Car", "Train")
                .flatMap(PublishSwitchOnExample::firstOperation)
                .publishOn(Schedulers.newBoundedElastic(10, 10, "publishOn_thread"))
                .flatMap(PublishSwitchOnExample::secondOperation)
                .flatMap(PublishSwitchOnExample::thirdOperation)
                .blockLast();
    }

    private static void subscribeOnWithPublishOn1() {
        Flux.just("Aircraft", "Car", "Train")
                .flatMap(PublishSwitchOnExample::firstOperation)
                .publishOn(Schedulers.newBoundedElastic(10, 10, "publishOn_thread"))
                .flatMap(PublishSwitchOnExample::secondOperation)
                .subscribeOn(Schedulers.newBoundedElastic(10, 10, "subscribeOn_thread"))
                .flatMap(PublishSwitchOnExample::thirdOperation)
                .blockLast();
    }

    private static void subscribeOnWithPublishOn2() {
        Flux.just("Aircraft", "Car", "Train")
                .flatMap(PublishSwitchOnExample::firstOperation)
                .publishOn(Schedulers.newBoundedElastic(10, 10, "publishOn_thread"))
                .flatMap(PublishSwitchOnExample::secondOperation)
                .flatMap(PublishSwitchOnExample::thirdOperation)
                .subscribeOn(Schedulers.newBoundedElastic(10, 10, "subscribeOn_thread"))
                .blockLast();
    }

    public static void main(String[] args) {
//        mainThread();
//        subscribeOn();
//        publishOn();
//        subscribeOnWithPublishOn1();
//        subscribeOnWithPublishOn2();
    }
}
