package com.example.reactive01.study03;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Slf4j
public class FluxScEx {
    public static void main(String[] args) throws InterruptedException {
//        Flux.range(1, 10)
//                .publishOn(Schedulers.newSingle("pub"))  //컨슈머가 느릴경우에 사용
//                .log()
//                //.subscribeOn(Schedulers.newSingle("sub"))   //퍼블리셔가 느릴경우에 사용
//                .subscribe(System.out::println);
//
//        System.out.println("exit");

        Flux.interval(Duration.ofMillis(200))
                .take(10)
                .subscribe(s -> log.debug("onNext:{}", s));

        TimeUnit.SECONDS.sleep(10);

//        Executors.newSingleThreadExecutor().execute(() -> {
//            try {
//                TimeUnit.SECONDS.sleep(2);
//            } catch (InterruptedException e) {
//            }
//            System.out.println("Hello");
//        });
//
//        System.out.println("exit");
    }
}