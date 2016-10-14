package com.tclcom.oneshop

import org.jetbrains.anko.*
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.tclcom.oneshop.Activitys.MainPageActivity
import com.tclcom.oneshop.MovieDemo.Services.Entity.MovieEntity
import com.tclcom.oneshop.MovieDemo.Services.MovieService
import com.tclcom.oneshop.middlewares.KOEvent
import rx.Subscriber

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super<AppCompatActivity>.onCreate(savedInstanceState)
        MainActivityUI().setContentView(this)
    }

}

class MainActivityUI : AnkoComponent<MainActivity> {
    override fun createView(ui: AnkoContext<MainActivity>) = with(ui) {
        verticalLayout {
            //padding = R.dimen.activity_vertical_margin

            button("Kedux Demo - Movie!") {
                onClick {
                    startActivity<MainPageActivity>()
                }
            }

//            button("Clink") {
//                onClick {
//                    val subscriber = object : Subscriber<MovieEntity>() {
//                        override fun onCompleted() {
//                            Log.d("MainActivityUI", "Http Get Completd!")
//                        }
//
//                        override fun onError(e: Throwable) {
//                            Log.e("MainActivityUI", e.getStackTraceString())
//                        }
//
//                        override fun onNext(movieEntity: MovieEntity) {
//                            Log.d("MainActivityUI", movieEntity.toString())
//                        }
//                    }
//
//                    MovieService.instance.getTopMovie(subscriber, 0, 1)
//                }
//            }
//
//            button("onClink") {
//                onClick {
//                    MovieService.instance.getTopMovie(null, 0, 1) { expr ->
//                        when(expr) {
//                            is KOEvent.Error -> Log.e("MainActivityUI", expr.e.getStackTraceString())
//                            is KOEvent.Next -> Log.d("MainActivityUI", expr.data.toString())
//                            is KOEvent.Completed -> Log.d("MainActivityUI", "Http Get Completd!")
//                        }
//
//                    }
//                }
//            }
        }
    }
}