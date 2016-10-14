package com.tclcom.kedux.middlewares

import com.tclcom.kedux.core.Action
import com.tclcom.kedux.core.Store

/**
* Created by jiangduan on 16/10/14.
*/

class StateSaverMiddleware(private var saver: SaveState) : Store.Middleware {

    interface SaveState {
        fun save(state: Map<String, Any?>)
    }

    override fun dispatch(store: Store, action: Action, next: Store.NextDispatcher?) {
        next?.dispatch(action)
        val state = store.getState()
        if (state != null) saver.save(state)
    }

    companion object {

        fun create(boclk: (Map<String, Any?>) -> Unit): StateSaverMiddleware {
            return StateSaverMiddleware(object : SaveState {
                override fun save(state: Map<String, Any?>) {
                    boclk(state)
                }
            })
        }
    }
}