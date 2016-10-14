package com.tclcom.kedux.components


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

interface IXCoreComponent {

    /**
     * 创建View

     * @param inflater
     * *
     * @param container
     * *
     * @return
     */
    fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?): View

    /**
     * 在此做View的初始化操作

     * @param view
     */
    fun onViewCreated(view: View)


}
