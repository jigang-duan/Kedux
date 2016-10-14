package com.tclcom.kedux.middlewares

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.tclcom.kedux.core.Action
import com.tclcom.kedux.core.Store
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

class LoggerMiddleware(private val tag: String, private val section: String? = null) : Store.Middleware {

    private val gson: Gson
    private val logger: AnkoLogger

    init {
        val gsonBuilder = GsonBuilder()
        gsonBuilder.setPrettyPrinting()
        gson = gsonBuilder.create()
        logger = object : AnkoLogger {
            override val loggerTag: String = tag
        }
    }

    override fun dispatch(store: Store, action: Action, next: Store.NextDispatcher?) {
        logger.info("==================================================================")
        logger.info("--> Action")
        logger.info(gson.toJson(action))
        next?.dispatch(action)
        logger.info("------------------------------------------------------------------")
        logger.info("<-- State")
        val state = store.getState()
        if (section == null) {
            logger.info(gson.toJson(state))
        } else if (state != null) {
            logger.info { gson.toJson(state[section]) }
        }
        logger.info("==================================================================")
    }

}
