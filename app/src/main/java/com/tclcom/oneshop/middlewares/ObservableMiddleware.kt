package com.tclcom.oneshop.middlewares

import com.tclcom.kedux.core.Action
import com.tclcom.kedux.core.Store
import rx.Observable
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
* Created by jiangduan on 16/10/12.
*/
class ObservableMiddleware: Store.Middleware {

    override fun dispatch(store: Store, action: Action, next: Store.NextDispatcher?) {

        if (action.value !is Observable<*>) {
            next?.dispatch(action)
            return
        }

        val observable = action.value as Observable<Any?>
        toSubscribe(observable, KSubscriber { expr ->
            when(expr) {
                is KOEvent.Start -> {next?.dispatch(action.copy(value = AsynAction.Start))}
                is KOEvent.Error -> {next?.dispatch(action.copy(value = expr.e))}
                is KOEvent.Next -> {next?.dispatch(action.copy(value = expr.data))}
                is KOEvent.Completed -> {next?.dispatch(action.copy(value = AsynAction.Completed))}
            }
        })
    }

    //添加线程管理并订阅
    private fun <T> toSubscribe(o: Observable<T>, s: Subscriber<T>) {
        o.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s)
    }
}

enum class AsynAction {
    Start, Completed
}