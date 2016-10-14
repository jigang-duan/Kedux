package com.tclcom.oneshop.Activitys

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.tclcom.oneshop.components.container.MainPageUIComponent

class MainPageActivity : AppCompatActivity() {

    private var mMainPageUIComponent: MainPageUIComponent? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mMainPageUIComponent = MainPageUIComponent(this)
        setContentView(mMainPageUIComponent)
    }
}
