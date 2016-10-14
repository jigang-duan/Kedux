package com.tclcom.oneshop.middlewares

import rx.Subscriber


class KSubscriber<T>(val sbufun: ((KOEvent<T>)->Unit)? = null) : Subscriber<T>() {

    override fun onStart() {
        sbufun?.invoke(KOEvent.Start)
    }

    override fun onError(e: Throwable?) {
        sbufun?.invoke(KOEvent.Error(e ?: Throwable("K Observable Error Event!!")))
    }

    override fun onNext(t: T) {
        sbufun?.invoke(KOEvent.Next(t))
    }

    override fun onCompleted() {
        sbufun?.invoke(KOEvent.Completed)
    }
}


sealed class KOEvent<out T> {
    object Start: KOEvent<Nothing>()
    object Completed: KOEvent<Nothing>()
    class Next<out T>(val data: T): KOEvent<T>()
    class Error(val e: Throwable): KOEvent<Nothing>()
}