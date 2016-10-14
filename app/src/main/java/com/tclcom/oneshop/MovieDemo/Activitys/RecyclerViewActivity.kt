package com.tclcom.oneshop.MovieDemo.Activitys

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.squareup.otto.Subscribe
import com.tclcom.kedux.components.Recycler.impl.XCoreRecyclerViewComponent
import com.tclcom.kedux.core.Action
import com.tclcom.kedux.core.Reducers
import com.tclcom.kedux.core.Store
import com.tclcom.kedux.eventbus.XCoreBus
import com.tclcom.kedux.middlewares.LoggerMiddleware
import com.tclcom.kedux.middlewares.StateSaverMiddleware
import com.tclcom.oneshop.MovieDemo.DemoApplication
import com.tclcom.oneshop.R
import com.tclcom.oneshop.MovieDemo.actions.MovieActionCreator
import com.tclcom.oneshop.MovieDemo.components.container.HeaderComponent
import com.tclcom.oneshop.MovieDemo.components.item.ImageItemComponent
import com.tclcom.oneshop.MovieDemo.components.item.TextItemComponent
import com.tclcom.oneshop.middlewares.ObservableMiddleware
import com.tclcom.oneshop.MovieDemo.model.Movie
import com.tclcom.oneshop.MovieDemo.model.Title
import com.tclcom.oneshop.MovieDemo.reducers.MovieReducer
import org.jetbrains.anko.find


class RecyclerViewActivity : AppCompatActivity() {

    private var recyclerComponent: XCoreRecyclerViewComponent? = null
    private var headerComponent: HeaderComponent? = null

    private var moviesCoreStore: Store? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        XCoreBus.instance.registerComponent(this)
        setContentView(R.layout.recycler_view_activity)

        //创建数据源的store
        moviesCoreStore = Store.create(
                Reducers(MovieReducer()),
                //hashMapOf(),
                DemoApplication.instance.globalStore,
                LoggerMiddleware(MovieReducer::class.java.simpleName),
                StateSaverMiddleware.create { DemoApplication.instance.globalStore = it },
                ObservableMiddleware())


        //创建RecyclerView的UI组件
        recyclerComponent = find(R.id.recycler_view_component)
        //注册item组件模板
        recyclerComponent?.apply {
            registerItemComponent(TextItemComponent())
            registerItemComponent(ImageItemComponent())
        }

        //创建头部组件
        headerComponent = find(R.id.recycler_view_header_component)

        //添加观察者
        moviesCoreStore?.apply {
            subscribe(MovieReducer.TYPE, recyclerComponent!!)
            subscribe(MovieReducer.TYPE, headerComponent!!)
        }
        //test()
        moviesCoreStore?.dispatch(MovieActionCreator.setValid())
    }

    override fun onDestroy() {
        if (moviesCoreStore?.getState() != null) {

        }
        super.onDestroy()
        moviesCoreStore?.unSubscribe(MovieReducer.TYPE, recyclerComponent!!)
        moviesCoreStore?.unSubscribe(MovieReducer.TYPE, headerComponent!!)
        XCoreBus.instance.unregisterComponent(this)
    }

    @Subscribe
    fun transportAction(action: Action) {
        moviesCoreStore?.dispatch(action)
    }

    private fun test() {
        moviesCoreStore?.apply {
            dispatch(MovieActionCreator.addCategory(Title("剧情")))
            dispatch(MovieActionCreator.addMovie(Movie("一休", null, "156 6666 6666", "xxxxxxxxx")))
            dispatch(MovieActionCreator.addMovie(Movie("一休2", null, "157 7777 7777", "xxxxxxxxx")))
        }
    }


}
