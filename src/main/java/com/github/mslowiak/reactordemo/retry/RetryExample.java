package com.github.mslowiak.reactordemo.retry;

import lombok.SneakyThrows;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.time.LocalDateTime;

public class RetryExample {

    @SneakyThrows
    private static Mono<String> someMethod() {
        System.out.println(LocalDateTime.now() + " - attempt");
        Thread.sleep(500);
        return Mono.error(new RuntimeException("Some exception"));
    }

    private static void retryWhenBackOffExample() {
        System.out.println(LocalDateTime.now() + " - STARTED");
        Mono.defer(RetryExample::someMethod)
                .retryWhen(Retry.backoff(4, Duration.ofSeconds(500)))
                .block();
        System.out.println(LocalDateTime.now() + " - STOPPED");
    }

    private static void retryWhenFixedDelayExample() {
        System.out.println(LocalDateTime.now() + " - STARTED");
        Mono.defer(RetryExample::someMethod)
                .retryWhen(Retry.fixedDelay(4, Duration.ofMillis(500)))
                .block();
        System.out.println(LocalDateTime.now() + " - STOPPED");
    }

    private static void simpleRetry() {
        System.out.println(LocalDateTime.now() + " - STARTED");
        Mono.defer(RetryExample::someMethod)
                .retry(2)
                .block();
        System.out.println(LocalDateTime.now() + " - STOPPED");
    }

    public static void main(String[] args) {
//        simpleRetry();
//        retryWhenFixedDelayExample();
//        retryWhenBackOffExample();
    }
}
