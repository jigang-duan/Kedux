package com.tclcom.oneshop.MovieDemo.actions

import com.tclcom.kedux.core.Action
import com.tclcom.oneshop.MovieDemo.DemoApplication
import com.tclcom.oneshop.MovieDemo.model.Movie
import com.tclcom.oneshop.MovieDemo.model.Title
import com.tclcom.oneshop.MovieDemo.model.wrapper.MovieWrapper

object MovieActionCreator {

    val ADD_ITEM = "addMovie"
    val ADD_TITLE = "addCategory"
    val DELETE_LAST = "deleteLast"
    val CHECK_BOX = "movieCheck"
    val FETCH_ITEM = "fetchMovie"
    val VALID = "Vaild"


    fun addMovie(movie: Movie): Action {
        return Action(ADD_ITEM, movie)
    }

    fun addCategory(title: Title): Action {
        return Action(ADD_TITLE, title)
    }

    fun deleteLast(): Action {
        return Action(DELETE_LAST)
    }

    fun checkBoxClick(movieWrapper: MovieWrapper): Action {
        return Action(CHECK_BOX, movieWrapper)
    }

    fun fetchMovie(start: Int): Action {
        return Action(FETCH_ITEM, DemoApplication.instance.movieRepository.getOneMovie(start))
    }

    fun setValid(): Action {
        return Action(VALID)
    }
}
