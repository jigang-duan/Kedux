package com.tclcom.oneshop.MovieDemo.repository

import com.tclcom.oneshop.MovieDemo.model.Movie
import rx.Observable

/**
* Created by jiangduan on 16/10/13.
*/
interface IMovieRepository {
    fun getOneMovie(start: Int): Observable<Movie>
}