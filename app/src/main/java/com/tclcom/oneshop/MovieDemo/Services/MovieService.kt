package com.tclcom.oneshop.MovieDemo.Services


import com.tclcom.oneshop.MovieDemo.Services.APIs.MovieApi
import com.tclcom.oneshop.MovieDemo.Services.Entity.MovieEntity
import com.tclcom.oneshop.middlewares.KOEvent
import com.tclcom.oneshop.middlewares.KSubscriber
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import rx.Observable
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

import java.util.concurrent.TimeUnit

class MovieService private constructor() {

    private val movieApi: MovieApi

    init {
        //手动创建一个OkHttpClient并设置超时时间
        val httpClientBuilder = OkHttpClient.Builder()
        httpClientBuilder.connectTimeout(DEFAULT_TIMEOUT.toLong(), TimeUnit.SECONDS)
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))

        val retrofit = Retrofit.Builder()
                .client(httpClientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(BASE_URL).build()

        movieApi = retrofit.create(MovieApi::class.java)
    }

    /**
     * 用于获取豆瓣电影Top250的数据
     * @param subscriber 由调用者传过来的观察者对象
     * *
     * @param start 起始位置
     * *
     * @param count 获取长度
     */
    fun rx_getTopMovie(start: Int, count: Int): Observable<MovieEntity> {
        return movieApi.getTopMovie(start, count)
    }


//    fun getTopMovie(subscriber: Subscriber<MovieEntity>? = null,
//                    start: Int, count: Int,
//                    sbufun: ((KOEvent<MovieEntity>)->Unit)? = null) {
//
//        val kSubscriber = KSubscriber(sbufun)
//
//        val observable = movieApi.getTopMovie(start, count)
//        val sub = subscriber ?: kSubscriber
//        toSubscribe(observable, sub)
//
//    }
//
//    //添加线程管理并订阅
//    private fun <T> toSubscribe(o: Observable<T>, s: Subscriber<T>) {
//        o.subscribeOn(Schedulers.io())
//                .unsubscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(s)
//    }

    companion object {
        val BASE_URL = "https://api.douban.com/v2/movie/"

        private val DEFAULT_TIMEOUT = 5

        //获取单例
        val instance: MovieService by lazy { MovieService() }
    }
}


