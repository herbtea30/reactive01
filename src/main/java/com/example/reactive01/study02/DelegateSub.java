package com.example.reactive01.study02;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/**
 * Name : DelegateSub <br/>
 * Description : Create a description.
 */
public class DelegateSub<T, R> implements Subscriber<T> {
    Subscriber sub;
    public DelegateSub(Subscriber<? super R> sub) {
        this.sub = sub;
    }

    @Override
    public void onSubscribe(Subscription s) {
        sub.onSubscribe(s);
    }

    @Override
    public void onNext(T i) {
        sub.onNext(i);
    }

    @Override
    public void onError(Throwable t) {
        sub.onError(t);
    }

    @Override
    public void onComplete() {
        sub.onComplete();
    }
}
