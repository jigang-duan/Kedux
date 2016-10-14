package com.tclcom.oneshop;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

//    @Test
//    public void http_movie() throws Exception {
//
//        final CountDownLatch signal = new CountDownLatch(1);
//
//        Subscriber subscriber = new Subscriber<MovieEntity>() {
//            @Override
//            public void onCompleted() {
//                Log.d("http_movie", "Http Get Completd!");
//                signal.countDown();
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                e.printStackTrace();
//            }
//
//            @Override
//            public void onNext(MovieEntity movieEntity) {
//                Log.d("http_movie",movieEntity.toString());
//            }
//        };
//
//        try {
//            MovieService.Companion.getInstance().getTopMovie(subscriber, 0, 10);
//            signal.await();
//        } catch (InterruptedException e) {
//            // TODO Auto-generated catch block
//            fail();
//            e.printStackTrace();
//        }
//
//    }
}