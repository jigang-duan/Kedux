package com.tclcom.kedux.core

import java.util.*

/**
* Created by jiangduan on 16/10/9.
*/

class Store private constructor(private val mIReducer: Reducers     //数据处理器-reducer
                                , state: Map<String, Any?>, middlewares: Array<out Store.Middleware>) {
    private val listeners = arrayListOf<IStateChangeListener>()      //观察者
    private val singleListeners = hashMapOf<String, ArrayList<ISingleStateChangeListener>>()
    private var state: Map<String, Any?>?

    private val next = arrayListOf<NextDispatcher>()

    init {
        this.state = state

        this.next.add(object : Store.NextDispatcher {
            override fun dispatch(action: Action) {
                this@Store.dispatcher.dispatch(this@Store, action, null)
            }
        })

        for (mv in middlewares) {
            val n = next[0]
            next.add(0, object : Store.NextDispatcher {
                override fun dispatch(action: Action) {
                    mv.dispatch(this@Store, action, n)
                }
            })
        }

    }

    fun dispatch(action: Action) {
        this.next[0].dispatch(action)
    }


    private val dispatcher = object : Middleware {
        override fun dispatch(store: Store, action: Action, next: Store.NextDispatcher?) {
            store.dispatch0(action)
        }
    }

    /**
     * 内部dispatch

     * @param action
     */
    @Throws(Throwable::class)
    private fun dispatchAction(action: Action) {

        synchronized(this) {
            state = mIReducer.reduce(state!!, action) { k,v ->
                singleListeners[k]?.forEach {
                    it.onStateChanged(v)
                }
            }
        }

        for (listener in listeners) {
            listener.onStateChanged(state!!)
        }
    }

    fun dispatch0(action: Action) {
        try {
            dispatchAction(action)
        } catch (e: Throwable) {
            e.printStackTrace()
        }

    }

    /**
     * 注册接口;添加观察者,当state改变时,通知观察者

     * @param listener
     */
    fun subscribe(listener: IStateChangeListener) {
        listeners.add(listener)
    }

    fun subscribe(single: String , listener: ISingleStateChangeListener) {
        var listeners = singleListeners[single]
        if (listeners == null) {
            listeners = arrayListOf<ISingleStateChangeListener>()
            singleListeners[single] = listeners
        }
        listeners.add(listener)
    }

    /**
     * 注销

     * @param listener
     */
    fun unSubscribe(listener: IStateChangeListener) {
        listeners.remove(listener)
    }

    fun unSubscribe(single: String , listener: ISingleStateChangeListener) {
        singleListeners[single]?.remove(listener)
    }

    fun getState(): Map<String, Any?>? {
        return this.state
    }


    /**
     * 状态改变的回调接口

     */
    interface IStateChangeListener {
        fun onStateChanged(state: Map<String, Any?>)
    }

    interface ISingleStateChangeListener {
        fun onStateChanged(state: Any?)
    }

    /**
     * 中间件接口
     */
    interface Middleware {
        fun dispatch(store: Store, action: Action, next: Store.NextDispatcher?)
    }

    interface NextDispatcher {
        fun dispatch(action: Action)
    }

    companion object {

        /**
         * 创建Store

         * @param reducer
         * *
         * @param initialState
         * *
         * @return
         */
        fun create(reducer: Reducers, initialState: Map<String, Any?>, vararg middlewares: Middleware): Store {
            return Store(reducer, initialState, middlewares)
        }
    }

}

