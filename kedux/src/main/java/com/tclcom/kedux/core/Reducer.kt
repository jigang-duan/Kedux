package com.tclcom.kedux.core

import com.tclcom.kedux.core.Action

/**
* Created by jiangduan on 16/10/9.
*/

interface Reducer {
    operator fun invoke(state: Any?, action: Action): Any?
}

class Reducers(vararg reducers: Reducer) {

    internal var map = mutableMapOf<String, Reducer>()


    init {
        reducers.forEach { map[it.javaClass.simpleName] = it }
    }

    fun reduce(state: Map<String, Any?>, action: Action, change: ((String, Any?)->Unit)? = null): Map<String, Any?> {
        val newState = mutableMapOf<String, Any?>()

        map.forEach {
            val key = it.key
            val nS = it.value.invoke(state[key], action)
            newState[key] = nS
            change?.let { it(key, nS) }
        }

        return newState
    }

}

