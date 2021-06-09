package com.example.reactive01.study02;

import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Name : PubSub <br/>
 * Description : Create a description.
 *
 * Reactive Streams - Operator
 *
 * pub -> [Data1] -> mapPub -> [Data2] -> logSub
 *
 * 1.map (d1 -> f -> d2)
 *
 */
@Slf4j
public class PubSub {
    public static void main(String[] args) {
        Publisher<Integer> pub = iterPub(Stream.iterate(1, a -> a + 1).limit(10).collect(Collectors.toList()));
        //Publisher<Integer> mapPub = mapPub(pub, s -> s * 10);
        //Publisher<String> mapPub = mapPub(pub, s ->  "[" + s + "]");
        //Publisher<Integer> sumPub = sumPub(pub);
        Publisher<StringBuilder> reducePub = reducePub(pub, new StringBuilder(), (a,b) -> a.append(b + ","));
        reducePub.subscribe(logSub());
    }

    private static <T, R> Publisher<R> reducePub(Publisher<T> pub, R init, BiFunction<R, T, R> bf) {
        return new Publisher<R>() {
            @Override
            public void subscribe(Subscriber<? super R> sub) {
                pub.subscribe(new DelegateSub<T, R>(sub) {
                    R result = init;

                    @Override
                    public void onNext(T i) {
                        result = (bf.apply(result, i));
                    }

                    @Override
                    public void onComplete() {
                        sub.onNext(result);
                        sub.onComplete();
                    }
                });
            }
        };
    }

//    private static <T, R> Publisher<R> mapPub(Publisher<T> pub, Function<T, R> f) {
//        return new Publisher<R>() {
//            @Override
//            public void subscribe(Subscriber<? super R> sub) {
//                pub.subscribe(new DelegateSub<T, R>(sub) {
//                    @Override
//                    public void onNext(T i) {
//                        sub.onNext(f.apply(i));
//                    }
//                });
//            }
//        };
//    }

    private static <T> Subscriber<T> logSub() {
        return new Subscriber<T>() {
            @Override
            public void onSubscribe(Subscription s) {
                log.debug("onSubscribe:");
                s.request(Long.MAX_VALUE);
            }

            @Override
            public void onNext(T i) {
                log.debug("onNext:{}", i);
            }

            @Override
            public void onError(Throwable t) {
                log.debug("onError:{}", t);
            }

            @Override
            public void onComplete() {
                log.debug("onComplete");

            }
        };
    }

    private static Publisher<Integer> iterPub(List<Integer> iter) {
        return new Publisher<Integer>() {
            @Override
            public void subscribe(Subscriber<? super Integer> sub) {
                sub.onSubscribe(new Subscription() {
                    @Override
                    public void request(long n) {
                        try {
                            iter.forEach(s -> sub.onNext(s));
                            sub.onComplete();
                        } catch (Throwable t) {
                            sub.onError(t);
                        }
                    }

                    @Override
                    public void cancel() {

                    }
                });
            }
        };
    }
}
