package com.example.interviewproject.util;

import java.util.concurrent.Callable;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by jinyoung on 2015-12-04.
 */
public class RxUtil {

    public static <T> Observable<T> makeObservable(final Callable<T> func){
        return Observable.create(
                new Observable.OnSubscribe<T>(){

                    @Override
                    public void call(Subscriber<? super T> subscriber) {
                        try {
                            T observed = func.call();
                            if(observed != null){
                                subscriber.onNext(observed);
                            }
                            subscriber.onCompleted();
                        } catch (Exception e) {
                            subscriber.onError(e);
                        }
                    }
                }

        );
    }
}