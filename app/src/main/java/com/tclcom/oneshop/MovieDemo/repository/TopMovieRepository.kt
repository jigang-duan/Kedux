package com.tclcom.oneshop.MovieDemo.repository


import com.tclcom.oneshop.MovieDemo.Services.MovieService
import com.tclcom.oneshop.MovieDemo.model.Movie
import rx.Observable

/**
* Created by jiangduan on 16/10/12.
*/
class TopMovieRepository(val service: MovieService): IMovieRepository {

    override fun getOneMovie(start: Int): Observable<Movie> {
        return service.rx_getTopMovie(start, 1).map { value ->
            Movie(value.subjects!![0].title!!, value.subjects[0].images?.small!!, value.subjects[0].casts!![0].name!!, value.subjects[0].directors!![0].name!!)
        }
    }

}