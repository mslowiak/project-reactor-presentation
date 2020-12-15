package com.github.mslowiak.reactordemo.debug;

import reactor.core.publisher.Flux;

import java.util.List;

public class DebugExample {


    private static void nullPointerWithCheckpoint() {
        final List<Integer> list = List.of(1, 2, 3, 4);
        Flux.just(1, 2, 3, 4, 5)
                .checkpoint("Before get list")
                .map(list::get)
                .checkpoint("After get list")
                .blockLast();
    }

    private static void debugWithLog() {
        Flux.just(1, 2, 3, 4, 5)
                .log()
                .map(number -> number * number)
                .log()
                .map(number -> "Number: " + number)
                .blockLast();
    }

    private static void productionDebugging() {
//        https://github.com/reactor/reactor-core/blob/master/docs/asciidoc/debugging.adoc#production-ready-global-debugging
    }

    public static void main(String[] args) {
//        nullPointerWithCheckpoint();
//        debugWithLog();
    }

}
