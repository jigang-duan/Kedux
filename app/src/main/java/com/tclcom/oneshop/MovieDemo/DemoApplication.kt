package com.tclcom.oneshop.MovieDemo

import android.app.Application
import com.tclcom.oneshop.MovieDemo.Services.MovieService
import com.tclcom.oneshop.MovieDemo.repository.IMovieRepository
import com.tclcom.oneshop.MovieDemo.repository.TopMovieRepository
import com.tclcom.oneshop.helper.DelegatesExt

/**
* Created by jiangduan on 16/10/13.
*/
class DemoApplication: Application() {

    companion object {
        var instance: DemoApplication by DelegatesExt.notNullSingleValue(DemoApplication::class.java.simpleName)
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    val movieRepository: IMovieRepository by lazy {
        TopMovieRepository(MovieService.instance)
    }

    var globalStore: Map<String, Any?> = hashMapOf()

}