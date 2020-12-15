package com.github.mslowiak.reactordemo.testing;

import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.time.Duration;

class TestingTest {

    private final Testing testing = new Testing();

    @Test
    void shouldFilterNamesStartingWithH() {
        StepVerifier.create(testing.getNamesNotStartingFromH())
                .expectNext("Jack")
                .expectNext("John")
                .verifyComplete();
    }

    @Test
    void shouldThrowException() {
        StepVerifier.create(testing.getException())
                .expectError(RuntimeException.class)
                .verify();
    }

    @Test
    void shouldConsumeDelayedCurrencies() {
        StepVerifier.withVirtualTime(testing::delayedCurrencyValue)
                .expectSubscription()
                .expectNoEvent(Duration.ofHours(1))
                .expectNext(BigDecimal.valueOf(3.465))
                .thenAwait(Duration.ofHours(1))
                .expectNext(BigDecimal.valueOf(3.466))
                .thenAwait(Duration.ofHours(1))
                .expectNext(BigDecimal.valueOf(3.472))
                .verifyComplete();
    }

}
