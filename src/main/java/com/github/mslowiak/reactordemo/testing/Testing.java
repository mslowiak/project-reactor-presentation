package com.github.mslowiak.reactordemo.testing;

import reactor.core.publisher.Flux;

import java.math.BigDecimal;
import java.time.Duration;

public class Testing {

    Flux<String> getNamesNotStartingFromH() {
        return Flux.just("Jack", "Harry", "John")
                .filter(name -> !name.startsWith("H"));
    }

    Flux<String> getException() {
        return Flux.error(new RuntimeException("Error"));
    }

    Flux<BigDecimal> delayedCurrencyValue() {
        return Flux.just(
                BigDecimal.valueOf(3.465),
                BigDecimal.valueOf(3.466),
                BigDecimal.valueOf(3.472)
        ).delayElements(Duration.ofHours(1));
    }

}
