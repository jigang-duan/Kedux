package com.tclcom.oneshop.MovieDemo.reducers

import com.tclcom.kedux.components.Recycler.XCoreRecyclerAdapter
import com.tclcom.kedux.core.Action
import com.tclcom.kedux.core.Reducer
import com.tclcom.oneshop.MovieDemo.Services.Entity.MovieEntity
import com.tclcom.oneshop.MovieDemo.actions.MovieActionCreator
import com.tclcom.oneshop.middlewares.AsynAction
import com.tclcom.oneshop.MovieDemo.model.Movie
import com.tclcom.oneshop.MovieDemo.model.Title
import com.tclcom.oneshop.MovieDemo.model.wrapper.MovieWrapper
import com.tclcom.oneshop.MovieDemo.model.wrapper.TitleWrapper
import java.util.*

class MovieReducer : Reducer {

    companion object {
        val TYPE = MovieReducer::class.java.simpleName
    }

    data class States(
            val isFetching: Boolean = false,
            val didInvalidate: Boolean = false,
            val lastUpdated: Date,
            override val items: List<XCoreRecyclerAdapter.IDataWrapper>) : XCoreRecyclerAdapter.IDataItems


    private fun ftchOneMove(states: States, value: Any?): States {
        when(value) {
            AsynAction.Start -> return states.copy(isFetching = true)
            AsynAction.Completed -> return states.copy(isFetching = false)
            is Throwable -> return states.copy(isFetching = false)
            is Movie -> return this.addOneMovie(states.copy(lastUpdated = Date()), value)
        }
        return states
    }

    /**
     * 添加联系人

     * @param movieWrappers
     * *
     * @param movie
     * *
     * @return
     */
    private fun addOneMovie(movieWrappers: List<XCoreRecyclerAdapter.IDataWrapper>, movie: Movie?): List<XCoreRecyclerAdapter.IDataWrapper> {
        if (movie == null) return movieWrappers
        val wrappers = ArrayList(movieWrappers)
        val movieWrapper = MovieWrapper()
        movieWrapper.movie = movie
        wrappers.add(movieWrapper)
        return wrappers
    }

    private fun addOneMovie(states: States, movie: Movie?): States {
        return states.copy(items = this.addOneMovie(states.items, movie))
    }

    /**
     * 添加标题

     * @param movieWrappers
     * *
     * @param value
     * *
     * @return
     */
    private fun addOneTitle(movieWrappers: List<XCoreRecyclerAdapter.IDataWrapper>, value: Title?): List<XCoreRecyclerAdapter.IDataWrapper> {
        if (value == null) return movieWrappers
        val wrappers = ArrayList(movieWrappers)
        val titleWrapper = TitleWrapper()
        titleWrapper.title = value
        wrappers.add(titleWrapper)
        return wrappers
    }

    private fun addOneTitle(states: States, value: Title?): States {
        return states.copy(items = this.addOneTitle(states.items, value))
    }

    /**
     * 删除最后一个

     * @param movieWrappers
     * *
     * @return
     */
    private fun deleteLast(movieWrappers: List<XCoreRecyclerAdapter.IDataWrapper>): List<XCoreRecyclerAdapter.IDataWrapper> {
        val wrappers = ArrayList(movieWrappers)
        if (wrappers.size > 0) {
            wrappers.removeAt(wrappers.size - 1)
        }
        return wrappers
    }

    private fun deleteLast(states: States): States {
        return states.copy(items = this.deleteLast(states.items))
    }

    private fun vaild(states: States): States {
        return states.copy(didInvalidate = false)
    }

    /**
     * 设置选择状态

     * @param movieWrappers
     * *
     * @param value
     * *
     * @return
     */
    private fun changeCheckBoxStatus(movieWrappers: List<XCoreRecyclerAdapter.IDataWrapper>, value: MovieWrapper?): List<XCoreRecyclerAdapter.IDataWrapper> {
        if (value == null) return movieWrappers
        value.isChecked = !value.isChecked
        return movieWrappers
    }

    private fun changeCheckBoxStatus(states: States, value: MovieWrapper?) : States {
        return states.copy(items = this.changeCheckBoxStatus(states.items, value))
    }

    override fun invoke(state: Any?, action: Action): Any? {
        var movieStates: States? = null
        try {
            movieStates = state as States?
        } catch (e: kotlin.TypeCastException) {
            e.printStackTrace()
        }
        if (movieStates == null) {
            movieStates = States(lastUpdated = Date(), items = arrayListOf<XCoreRecyclerAdapter.IDataWrapper>())
        }

        when (action.type) {
            MovieActionCreator.ADD_ITEM -> return addOneMovie(movieStates, action.value as Movie?)
            MovieActionCreator.ADD_TITLE -> return addOneTitle(movieStates, action.value as Title?)
            MovieActionCreator.DELETE_LAST -> return deleteLast(movieStates)
            MovieActionCreator.CHECK_BOX -> return changeCheckBoxStatus(movieStates, action.value as MovieWrapper?)
            MovieActionCreator.FETCH_ITEM -> return ftchOneMove(movieStates, action.value)
            MovieActionCreator.VALID -> return vaild(movieStates)
        }

        return state
    }
}
