package com.example.reactive01.study02;

import reactor.core.publisher.Flux;

/**
 * Name : ReactorEx <br/>
 * Description : Create a description.
 */
public class ReactorEx {
    public static void main(String[] args) {
        Flux.<Integer>create(e -> {
            e.next(1);
            e.next(2);
            e.next(3);
            e.complete();
        })
        .log()
        .filter(p -> p > 1)
        .map(s->s*10)
        .reduce(0, (a, b) -> a+b)
        .log()
        .subscribe(System.out::println);
    }
}
