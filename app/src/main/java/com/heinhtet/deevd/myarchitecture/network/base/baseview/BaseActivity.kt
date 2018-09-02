package com.heinhtet.deevd.myarchitecture.network.base.baseview

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

/**
 * Created by Hein Htet on 8/22/18.
 */

abstract class BaseActivity : AppCompatActivity() {


    abstract fun getLayoutId(): Int


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
    }

}