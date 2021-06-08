package com.example.reactive01.study01;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Name : Ob <br/>
 * Description : Create a description.
 */
public class Ob {

    //1. Complete??

    //2. ERROR??

    //Iterable <----> Observable(duality)
    //pulling <-----> push

    /*
        Iterable<Integer> iterable = () ->
            new Iterator<Integer>() {
                int i = 0;
                final static int MAX = 10;

                public boolean hasNext() {
                    return i < MAX;
                }

                public Integer next() {
                    return ++i;
                }
            };

        for(Integer i : iterable) { //for-each
            System.out.println(i);
        }

        for(Iterator<Integer> it = iterable.iterator();it.hasNext();){
            System.out.println(it.next());
        }
    */

    static class IntObservable extends Observable implements Runnable{

        @Override
        public void run() {
            for(int i=1;i<=10;i++) {
                setChanged();
                notifyObservers(i);
            }
        }
    }

    public static void main(String[] args) {
        Observer observer = new Observer() {
            @Override
            public void update(Observable o, Object arg) {
                System.out.println(Thread.currentThread().getName() + " " + arg);
            }
        };

        IntObservable intObservable = new IntObservable();
        intObservable.addObserver(observer);

        ExecutorService es = Executors.newSingleThreadExecutor();
        es.execute(intObservable);

        System.out.println(Thread.currentThread().getName() + " EXIT ");
        es.shutdown();
    }
}
