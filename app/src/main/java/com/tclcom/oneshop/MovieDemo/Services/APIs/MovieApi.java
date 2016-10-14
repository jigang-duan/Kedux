package com.tclcom.oneshop.MovieDemo.Services.APIs;

import com.tclcom.oneshop.MovieDemo.Services.Entity.MovieEntity;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;


public interface MovieApi {
    @GET("top250")
    Observable<MovieEntity> getTopMovie(@Query("start") int start, @Query("count") int count);
}
