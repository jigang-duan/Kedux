package com.tclcom.oneshop.helper

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
* Created by jiangduan on 16/10/13.
*/

private class NotNullSingleValueVar<T>(val desc: String? = null) : ReadWriteProperty<Any?, T> {
    private var value: T? = null
    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return value ?: throw IllegalStateException(desc + " not initialized")
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        this.value = if (this.value == null) value
        else throw IllegalStateException(desc + " already initialized")
    }
}

object DelegatesExt {
    fun <T> notNullSingleValue(desc: String): ReadWriteProperty<Any?, T> = NotNullSingleValueVar(desc)
}